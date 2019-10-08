package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ru.gbuac.AuthorizedUser;
import ru.gbuac.dao.*;
import ru.gbuac.model.*;
import ru.gbuac.to.*;
import ru.gbuac.util.*;
import ru.gbuac.util.exception.FileUploadException;
import ru.gbuac.util.exception.GeneratePdfException;
import ru.gbuac.util.exception.NotFoundException;
import ru.gbuac.util.exception.UnauthorizedUserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private DocRepository docRepository;

    @Autowired
    private DocValuedFieldsRepository docValuedFieldsRepository;

    @Autowired
    private DocTypeFieldsRepository docTypeFieldsRepository;

    @Autowired
    private DocTypeRepository docTypeRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private CatalogElemRepository catalogElemRepository;

    @Autowired
    private DocTypeRoutesRepository docTypeRoutesRepository;

    @Autowired
    private DocAgreementRepository docAgreementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FieldsStagesRepository fieldsStagesRepository;

    @Autowired
    private DocNumberPrefixesRepository docNumberPrefixesRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Value("${pdf.final.dir}")
    private String pdfDir;

    @Value("${pdf.temp.dir}")
    private String pdfTempDir;

    @Value("${docx.temp.dir}")
    private String docxTempDir;

    @Value("${doc.templates.dir}")
    private String docTemplatesDir;

    @Value("${uploads.temp.dir}")
    private String uploadsTempDir;

    @Value("${uploads.dir}")
    private String uploadsDir;

    @Override
    public Doc get(int id) throws NotFoundException {
        return checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
    }

    @Override
    public DocTo getFullByUserName(int id, String userName) throws NotFoundException {
        DocTo docTo = asDocTo(checkNotFoundWithId(docRepository.findById(id).orElse(null), id));
        DocStatus docStatus = docTo.getDocStatus();
        if (docStatus.equals(DocStatus.COMPLETED) || (docStatus.equals(DocStatus.AGREEMENT_REJECTED)))  {
            docTo.setCanAgree(false);
        }
        else {

            boolean hasRights = docTypeRoutesRepository.isHasRightsForDocTypeOnStage(docTo.getCurrentAgreementStage(),
                    docTo.getDocTypeId(), userName);
            docTo.setCanAgree(hasRights || AuthorizedUser.hasRole("ADMIN"));
        }
        return docTo;
    }

    @Override
    public List<Doc> getAllAgreementByUsername(String userName) {
        List<Doc> viewDocs = new ArrayList<>();
        for (DocTypeRoutes docTypeRoute: docTypeRoutesRepository.getByUserName(userName)) {
            viewDocs.addAll(docRepository.getAllAgreementByDocTypeAndStage(
                    docTypeRoute.getDocType().getId(), docTypeRoute.getAgreeStage()));
        }
        return viewDocs;
    }

    @Override
    public List<Doc> getAllAgreedByUsername(String userName) {
        List<Doc> viewDocs = new ArrayList<>();
        for (DocTypeRoutes docTypeRoute: docTypeRoutesRepository.getByUserName(userName)) {
            viewDocs.addAll(docRepository.getAllAgreedByDocTypeAndStage(
                    docTypeRoute.getDocType().getId(), docTypeRoute.getAgreeStage()));
        }
        return viewDocs;
    }

    @Override
    public List<Doc> getAllRegisteredByUsername(String userName) {
        List<Doc> viewDocs = new ArrayList<>();
        for (DocTypeRoutes docTypeRoute: docTypeRoutesRepository.getByUserName(userName)) {
            viewDocs.addAll(docRepository.getAllRegisteredByDocTypeAndStage(
                    docTypeRoute.getDocType().getId(), docTypeRoute.getAgreeStage()));
        }
        return viewDocs;
    }

    @Override
    public List<Doc> getAll() {
        return docRepository.getAll();
    }

    @Override
    public List<Doc> getAllAgreement() {
        return docRepository.getAllAgreement();
    }

    @Override
    public List<Doc> getAllRegistered() {
        return docRepository.getAllRegistered();
    }

    @Override
    public List<DocNumberTo> getRegNumbers() {
        return docRepository.getRegNumbers();
    }

    // Инкрементирование стадии согласования
    private Doc prepareToPersist(Doc doc, Integer currentAgreementStage, Integer finalStageForThisDocType) {
        if (!currentAgreementStage.equals(finalStageForThisDocType))
            doc.setCurrentAgreementStage(currentAgreementStage + 1);
        return doc;
    }

    @Override
    public DocTo save(DocTo docTo, String userName, String rootPath) throws UnauthorizedUserException {
        Assert.notNull(docTo, "doc must not be null");
        Integer finalStageForThisDocType = docTypeRoutesRepository.getFinalStageForDocType(docTo.getDocTypeId());

        boolean hasRights = AuthorizedUser.hasRole(docTypeRepository.findById(docTo.getDocTypeId()).orElse(null).getRole().getAuthority());
        if (!hasRights && !AuthorizedUser.hasRole("ADMIN")) {
            throw new UnauthorizedUserException();
        }

        docTo.setProjectRegNum(docNumberPrefixesRepository.generateDocNumber("согл", ""));
        Doc docToSave = prepareToPersist(createNewDocFromTo(docTo), 0, finalStageForThisDocType);

        Doc saved = docRepository.save(docToSave);
        DocTo savedTo = asDocTo(saved);
        String urlPdf = createPDFOrDocx(savedTo, rootPath, false, true);
        docRepository.setUrlPDF(savedTo.getId(), urlPdf);
        savedTo.setUrlPDF(urlPdf);
        savedTo.setCanAgree(hasRights);
        List<DocTypeRoutes> agreementListTemplate = docTypeRoutesRepository.getAgreementTemplate(docTo.getDocTypeId());
        for (DocTypeRoutes ae : agreementListTemplate) {
            docAgreementRepository.save(new DocAgreement(null, saved, ae.getUser()));
        }
        return savedTo;
    }

    @Override
    public DocTo update(DocTo docTo, int id, String userName, String rootPath) throws NotFoundException, UnauthorizedUserException {
        String comment = docTo.getComment();
        Assert.notNull(docTo, "docTo must not be null");
        int finalStageForThisDocType = docTypeRoutesRepository.getFinalStageForDocType(docTo.getDocTypeId());

        Doc updated = docFromTo(docTo);
        int currentAgreementStage = updated.getCurrentAgreementStage();

        if (finalStageForThisDocType == currentAgreementStage) {
            String docTypeMask = docNumberPrefixesRepository.getMaskByDocTypeId(docTo.getDocTypeId());
            String docNumber = "";
            if (docTo.getDocTypeId() == 1) {
                List<Doc> docs = docRepository.getAllByDocType(docTo.getDocTypeId());
                StringBuilder optional = new StringBuilder();
                for (DocValuedFields docValuedFields : updated.getDocValuedFields()) {
                    if (docValuedFields.getValuedField().getField().getId() == 4) {
                        LocalDate agendaDate = docValuedFields.getValuedField().getValueDate().toLocalDate();
                        optional.append(agendaDate.format(DateTimeFormatter.ofPattern("dd.MM")));
                        long agendaCountForDay = docs.stream()
                                .flatMap(f -> f.getDocValuedFields().stream())
                                .filter(f -> f.getValuedField().getField().getId() == 4)
                                .filter(f -> f.getValuedField().getValueDate().toLocalDate().equals(agendaDate))
                                .count();
                        optional.append("-");
                        optional.append(agendaCountForDay);
                        break;
                    }
                }
                docNumber = docNumberPrefixesRepository.generateDocNumber(docTypeMask, optional.toString());
            }
            else {
                docNumber = docNumberPrefixesRepository.generateDocNumber(docTypeMask, "");
            }
            updated.setRegNum(docNumber);
            updated.setRegDateTime(LocalDateTime.now());
            updated.setDocStatus(DocStatus.COMPLETED);
        } else {
            prepareToPersist(updated, currentAgreementStage, finalStageForThisDocType);
        }

        boolean hasRights = docTypeRoutesRepository.isHasRightsForDocTypeOnStage(currentAgreementStage,
                updated.getDocType().getId(), userName);
        if (!hasRights && !AuthorizedUser.hasRole("ADMIN")) {
            throw new UnauthorizedUserException();
        }

        // Генерация проектного PDF с добавлением ссылки в сущность, либо если isFinalStage, то
        // перемещаем файл из временного хранилища temp_uploads в хранилище pdf
        updated.setUrlPDF(createPDFOrDocx(asDocTo(updated), rootPath, false, true));
        docValuedFieldsRepository.deleteAll(id);
        updated = checkNotFoundWithId(docRepository.save(updated), id);

        DocAgreement docAgreement = docAgreementRepository.getLastForAgreeByUserName(userName);
        docAgreement.setComment(comment);
        docAgreement.setDecisionType(DecisionType.ACCEPTED);
        docAgreement.setAgreedDateTime(LocalDateTime.now());
        docAgreementRepository.save(docAgreement);

        DocTo updatedTo = asDocTo(updated);
        updatedTo.setCanAgree(hasRights);
        return asDocTo(updated);
    }

    public static void moveFile(String oldPath, String newPath) {
        new File(newPath).delete();
        File oldFile = new File(oldPath);
        oldFile.renameTo(new File(newPath));
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Assert.notNull(id, "doc must not be null");
        checkNotFoundWithId(docRepository.delete(id)!= 0, id);
    }

    @Override
    public DocTo returnDocAgreement(int id, String targetUserName, String userName, String comment) throws NotFoundException {
        Doc updated = checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
        int stage = docTypeRoutesRepository
                .getStageByUserNameForDocType(targetUserName, updated.getDocType().getId(),
                        updated.getCurrentAgreementStage());
        updated.setCurrentAgreementStage(stage);
        User user = userRepository.getByName(userName);
        User targetUser = userRepository.getByName(targetUserName);
        DocAgreement docAgreement = docAgreementRepository.getLastForAgreeByUserName(userName);
        docAgreement.setAgreedDateTime(LocalDateTime.now());
        docAgreement.setComment(comment);
        docAgreement.setReturnedUser(targetUser);
        docAgreement.setDecisionType(DecisionType.RETURNED);
        docAgreementRepository.save(docAgreement);
        docAgreementRepository.save(new DocAgreement(null, updated, targetUser));
        docAgreementRepository.save(new DocAgreement(null, updated, user));
        return asDocTo(checkNotFoundWithId(docRepository.save(updated), id));
    }

    @Override
    public DocTo rejectDocAgreement(int id, String userName, String comment) throws NotFoundException {
        Doc updated = checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
        updated.setDocStatus(DocStatus.AGREEMENT_REJECTED);
        User user = userRepository.getByName(userName);
        DocAgreement docAgreement = docAgreementRepository.getLastForAgreeByUserName(userName);
        docAgreement.setAgreedDateTime(LocalDateTime.now());
        docAgreement.setComment(comment);
        docAgreement.setDecisionType(DecisionType.REJECTED);
        docAgreementRepository.save(docAgreement);
        return asDocTo(checkNotFoundWithId(docRepository.save(updated), id));
    }

    private void fillTags(FieldTo fieldTo, Map<String, String> simpleTags, Map<String, TaggedTable> taggedTables, Integer maxCellsCount) {
        String tag = fieldTo.getTag();
        if (TagUtil.getTableTag(tag) != null) {
            Map<String, String> cellsTags;
            String tableTag = TagUtil.getTableTag(tag);
            if (!taggedTables.containsKey(tableTag)) {
                cellsTags = new HashMap<>();
                taggedTables.put(tableTag, new TaggedTable(tableTag, new ArrayList<TableRow>() {{
                    add(new TableRow(cellsTags));}}));
            } else {
                List<TableRow> rows = taggedTables.get(tableTag).getRows();
                if (rows.get(rows.size()-1).getCellsTags().size() == maxCellsCount) {
                    cellsTags = new HashMap<>();
                    rows.add((new TableRow(cellsTags)));
                }
                else
                {
                    cellsTags = rows.get(rows.size()-1).getCellsTags();
                }
            }
            switch (fieldTo.getFieldType()) {
                case GROUP_FIELDS:
                    for (FieldTo childField : fieldTo.getChildFields()) {
                        fillTags(childField, simpleTags, taggedTables, fieldTo.getChildFields().size());
                    }
                    break;
                case CATALOG:
                    CatalogElem catalogElemChild = null;
                    if (fieldTo.getValueInt() != null) {
                        catalogElemChild = catalogElemRepository.findById(fieldTo.getValueInt()).orElse(null);
                        switch (catalogElemChild.getCatalog().getCatalogType()) {
                            case TEXT:
                                cellsTags.put(tag, catalogElemChild.getValueStr());
                                break;
                            case NUMBER:
                                cellsTags.put(tag, catalogElemChild.getValueInt());
                                break;
                        }
                    }
                    else {
                        cellsTags.put(tag, "");
                    }
                    break;
                case CATALOG_USERS:
                    User user = userRepository.findById(fieldTo.getValueInt()).orElse(null);
                    cellsTags.put(tag, user.getFirstname().substring(0,1) + "." + user.getPatronym().substring(0,1) + ". " + user.getLastname() +
                            " " + "8(495)620-20-00, доб. " + user.getPhone());
                    break;
                case CATALOG_ORGANIZATIONS:
                    Organization organization = organizationRepository.findById(fieldTo.getValueInt()).orElse(null);
                    cellsTags.put(tag, organization.getShortName());
                    break;
                case CATALOG_REGNUMBERS:
                    String regNum = docRepository.findById(fieldTo.getValueInt()).orElse(null).getRegNum();
                    cellsTags.put(tag, regNum);
                    break;
                default:
                    if (!tag.equals("")) {
                        cellsTags.put(tag, fieldTo.getValueByFieldType());
                    }
                    break;
            }
        } else
        if (TagUtil.getSimpleTag(tag) != null) {
            switch (fieldTo.getFieldType()) {
                case GROUP_FIELDS:
                    for (FieldTo childField : fieldTo.getChildFields()) {
                        int childMaxCellsCount = (int) fieldTo.getChildFields().stream()
                                .filter(c -> !c.getTag().equals("")).count();
                        fillTags(childField, simpleTags, taggedTables, childMaxCellsCount);
                    }
                    break;
                case CATALOG:
                    CatalogElem catalogElemChild =
                            catalogElemRepository.findById(fieldTo.getValueInt()).orElse(null);
                    switch (catalogElemChild.getCatalog().getCatalogType()) {
                        case TEXT:
                            simpleTags.put(tag, catalogElemChild.getValueStr());
                            break;
                        case NUMBER:
                            simpleTags.put(tag, catalogElemChild.getValueInt());
                            break;
                    }
                case CATALOG_USERS:
                    User user = userRepository.findById(fieldTo.getValueInt()).orElse(null);
                    simpleTags.put(tag, user.getFirstname().substring(0,1) + "." + user.getPatronym().substring(0,1) + ". " + user.getLastname() +
                            " " + "8(495)620-20-00, доб. " + user.getPhone());
                    break;
                case CATALOG_ORGANIZATIONS:
                    Organization organization = organizationRepository.findById(fieldTo.getValueInt()).orElse(null);
                    simpleTags.put(tag, organization.getShortName());
                    break;
                case CATALOG_REGNUMBERS:
                    String regNum = docRepository.findById(fieldTo.getValueInt()).orElse(null).getRegNum();
                    simpleTags.put(tag, regNum);
                    break;
                default:
                    if (!tag.equals("")) {
                        simpleTags.put(tag, fieldTo.getValueByFieldType());
                    }
                    break;
            }
        }
    }

    @Override
    public FileTo uploadFile(MultipartFile inputFile, String rootPath) throws FileUploadException {
        String savePath = rootPath + uploadsTempDir + UUID.randomUUID().toString() + "_" + inputFile.getOriginalFilename();
        try {
            inputFile.transferTo(new File(savePath));
        }
         catch (IOException e) {
            throw new FileUploadException();
        }
        return new FileTo(savePath);
    }

    @Override
    public FileTo createDOCX(DocTo docTo, String rootPath) {
        return new FileTo(createPDFOrDocx(asDocTo(createNewDocFromTo(docTo)), rootPath,  true, false));
    }

    @Override
    public FileTo createPDF(DocTo docTo, String rootPath) {
        return new FileTo(createPDFOrDocx(asDocTo(docFromTo(docTo)), rootPath, true, true));
    }

    private String createPDFOrDocx(DocTo docTo, String rootPath, Boolean saveToTempDir, Boolean isPDF) {
        DocType docType = docTypeRepository.findById(docTo.getDocTypeId()).orElse(null);
        String templatePath = rootPath + docTemplatesDir + docType.getTemplateFileName();
        String tmpTemplatePath = rootPath + docTemplatesDir + docType.getTmpTemplateFileName();

        String genTemplatePath = isPDF ? templatePath : tmpTemplatePath;

        String pdfTempPath = rootPath + pdfTempDir + docTo.getId() + ".pdf";
        String pdfPath = rootPath + pdfDir + docTo.getId() + ".pdf";

        String pdfSavePath = saveToTempDir ? pdfTempPath : pdfPath;
        String docxSavePath = rootPath + docxTempDir + UUID.randomUUID().toString() + ".docx";

        String savePath = isPDF ? pdfSavePath : docxSavePath;

        Map<String, String> simpleTags = new HashMap<>();
        simpleTags.put("RegNum", Optional.ofNullable(docTo.getRegNum() != null ? docTo.getRegNum() : docTo.getProjectRegNum()).orElse(""));
        Map<String, TaggedTable> taggedTables = new HashMap<>();

        for (DocFieldsTo docFieldsTo : docTo.getChildFields()) {
            fillTags(docFieldsTo.getField(), simpleTags, taggedTables, docTo.getChildFields().size());
        }

        try {
            ByteArrayOutputStream byteArrayOutputStream =
                    Templater.fillTagsByDictionary(genTemplatePath, simpleTags, taggedTables, isPDF);
            byteArrayOutputStream.writeTo(new FileOutputStream(savePath));
        } catch (Exception e) {
            throw new GeneratePdfException();
        }

        return savePath.replace(rootPath,"");
    }

    private DocTo asDocTo(Doc doc) {
        Integer docTypeId = doc.getDocType().getId();
        Integer curAgreementStage = doc.getCurrentAgreementStage();
        boolean isFinalStage = docTypeRoutesRepository.getFinalStageForDocType(docTypeId) ==
                Optional.ofNullable(curAgreementStage).orElse(0);
        List<String> curUserRoles = AuthorizedUser.getRoles();
        List<DocValuedFields> docValuedFields = doc.getDocValuedFields();
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();
        List<FieldsStages> fieldsStages = fieldsStagesRepository.getAll(docTypeId);
        Map<Integer, FieldsStages> fMap = fieldsStages.stream().filter(f -> f.getAgreeStage().equals(curAgreementStage))
                .collect(Collectors.toMap(FieldsStages::getFieldId, f -> f));

        for (DocValuedFields d:docValuedFields) {
            docFieldsTos.add(new DocFieldsTo(d.getId(), FieldUtil.asTo(d.getValuedField(), curUserRoles, (HashMap<Integer, FieldsStages>) fMap), d.getPosition()));
        }

        return new DocTo(doc.getId(), doc.getRegNum(), doc.getRegDateTime(), doc.getProjectRegNum(),
                doc.getProjectRegDateTime(), doc.getInsertDateTime(), doc.getDocType().getId(),
                doc.getDocStatus(), curAgreementStage, isFinalStage, false, doc.getUrlPDF(), null,
                docFieldsTos);
    }

    public ValuedField createNewValueFieldFromTo(FieldTo newField) {
        List<ValuedField> childFields = new ArrayList<>();
        for (FieldTo childFieldTo : newField.getChildFields()) {
            childFields.add(createNewValueFieldFromTo(childFieldTo));
        }

        Field field = fieldRepository.findById(newField.getFieldId()).orElse(null);
        CatalogElem catalogElem = null;
        if (field.getFieldType() == FieldType.CATALOG) {
            if (newField.getValueInt() != null) {
                catalogElem = catalogElemRepository.findById(newField.getValueInt()).orElse(null);
                newField.setValueInt(null);
            }
        }

        return new ValuedField(null, childFields,  field, catalogElem, newField.getValueInt(), newField.getValueStr(),
                newField.getValueDate(), newField.getValueDate(), newField.getValueDate());
    }

    private Doc createNewDocFromTo(DocTo docTo) {
        DocType docType = docTypeRepository.findById(docTo.getDocTypeId()).orElse(null);
        Doc doc = new Doc(null, docTo.getRegNum(), docTo.getRegDateTime(), docTo.getProjectRegNum(),
                docTo.getProjectRegDateTime(), docTo.getInsertDateTime(), docType, null,
                docTo.getCurrentAgreementStage(), docTo.getUrlPDF());
        List<DocFieldsTo> docFieldsTos = docTo.getChildFields();
        List<DocValuedFields> docValuedFields = new ArrayList<>();

        for (DocFieldsTo d:docFieldsTos) {
            docValuedFields.add(new DocValuedFields(null, doc,
                    createNewValueFieldFromTo(d.getField()), d.getPosition()));
        }
        doc.setDocValuedFields(docValuedFields);
        doc.setCurrentAgreementStage(docTo.getCurrentAgreementStage());
        return doc;
    }

    private Doc docFromTo(DocTo docTo) {
        DocType docType = docTypeRepository.findById(docTo.getDocTypeId()).orElse(null);
        Doc exDoc = checkNotFoundWithId(docRepository.findById(docTo.getId()).orElse(null), docTo.getId());
        Doc doc = new Doc(exDoc.getId(), exDoc.getRegNum(), exDoc.getRegDateTime(), exDoc.getProjectRegNum(),
                exDoc.getProjectRegDateTime(), exDoc.getInsertDateTime(), docType, null,
                exDoc.getCurrentAgreementStage(), exDoc.getUrlPDF());
        List<DocFieldsTo> docFieldsTos = docTo.getChildFields();
        List<DocValuedFields> docValuedFields = new ArrayList<>();

        for (DocFieldsTo d:docFieldsTos) {
            docValuedFields.add(new DocValuedFields(null, doc,
                    createNewValueFieldFromTo(d.getField()), d.getPosition()));
        }
        doc.setDocValuedFields(docValuedFields);
        return doc;
    }
}
