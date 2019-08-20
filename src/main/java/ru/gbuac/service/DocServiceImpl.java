package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.*;
import ru.gbuac.model.*;
import ru.gbuac.to.DocFieldsTo;
import ru.gbuac.to.DocTo;
import ru.gbuac.to.FieldTo;
import ru.gbuac.util.FieldUtil;
import ru.gbuac.util.TableRow;
import ru.gbuac.util.TaggedTable;
import ru.gbuac.util.Templater;
import ru.gbuac.util.exception.NotFoundException;
import ru.gbuac.util.exception.UnauthorizedUserException;
import java.time.LocalDateTime;
import java.util.*;

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
    private RoleRepository roleRepository;

    @Value("${pdf.final.dir}")
    private String pdfFinalDir;

    @Value("${pdf.temp.dir}")
    private String pdfTempDir;

    @Value("${doc.templates.dir}")
    private String docTemplatesDir;

    @Override
    public Doc get(int id) throws NotFoundException {
        return checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
    }

    private DocTo asDocTo(Doc doc, String userName) {
        Integer docTypeId = doc.getDocType().getId();

        Integer curAgreementStage = doc.getCurrentAgreementStage();
        Boolean isFinalStage = docTypeRoutesRepository.getFinalStageForDocType(docTypeId) == curAgreementStage;

        List<Role> curUserRoles = roleRepository.getRolesByUsername(userName);
        List<DocValuedFields> docValuedFields = docValuedFieldsRepository.getAll(doc.getId());
        List<DocTypeFields> docTypeFields = docTypeFieldsRepository.getAll(docTypeId);
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();

        for (DocValuedFields d:docValuedFields) {
            Role role = docTypeFields.stream().filter(f -> f.getField().getId() == d.getValuedField().getField().getId())
                    .findAny().get().getRole();

            docFieldsTos.add(new DocFieldsTo(d.getId(), FieldUtil.asTo(d.getValuedField()),
                    d.getPosition(), curUserRoles.contains(role)));
        }

        return new DocTo(doc.getId(), doc.getRegNum(), doc.getRegDateTime(), doc.getProjectRegNum(),
                doc.getProjectRegDateTime(), doc.getInsertDateTime(),
                doc.getDocType().getId(), curAgreementStage, isFinalStage, doc.getUrlPDF(), docFieldsTos);
    }

    public ValuedField createNewValueFieldFromTo(FieldTo newField) {
        List<ValuedField> childFields = new ArrayList<>();
        for (FieldTo childFieldTo : newField.getChildFields()) {
            childFields.add(createNewValueFieldFromTo(childFieldTo));
        }

        Field field = fieldRepository.findById(newField.getFieldId()).orElse(null);
        CatalogElem catalogElem = null;
        if (field.getFieldType() == FieldType.CATALOG) {
            catalogElem = catalogElemRepository.findById(newField.getValueInt()).orElse(null);
            newField.setValueInt(null);
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

    @Override
    public DocTo getFullByUserName(int id, String userName) throws NotFoundException {
        return asDocTo(checkNotFoundWithId(docRepository.findById(id).orElse(null), id), userName);
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
    public List<Doc> getAll() {
        return docRepository.getAll();
    }

    private DocTo prepareToPersist(DocTo docTo) {
        Integer currentAgreementStage = docTo.getCurrentAgreementStage();
        Integer finalStageForThisDocType = docTypeRoutesRepository.getFinalStageForDocType(docTo.getDocTypeId());
        if (docTo.getRegNum() == null) {
            if (currentAgreementStage == null) {
                docTo.setCurrentAgreementStage(1);
                docTo.setFinalStage(false);
            } else {
                if (currentAgreementStage != finalStageForThisDocType)
                    docTo.setCurrentAgreementStage(currentAgreementStage + 1);
            }
        }
        return docTo;
    }

    @Override
    public DocTo save(DocTo docTo, String userName, String rootPath) throws UnauthorizedUserException {
        Assert.notNull(docTo, "doc must not be null");
        List<DocTypeFields> docTypeFields = docTypeFieldsRepository.getAll(docTo.getDocTypeId());
        List<Role> curUserRoles = roleRepository.getRolesByUsername(userName);

        Long existRole = 0L;
        for (DocFieldsTo d:docTo.getChildFields()) {
            existRole += docTypeFields.stream()
                    .filter(f -> f.getField().getId() == d.getField().getFieldId())
                    .filter(f -> curUserRoles.contains(f.getRole())).count();
        }
        if (existRole == 0) {
            throw new UnauthorizedUserException();
        }

        if (docTo.isFinalStage() != null && docTo.isFinalStage()) {
            docTo.setRegNum("ДЭПР-" + new Random().nextInt(100) + "/19");
            docTo.setRegDateTime(LocalDateTime.now());
        }
        else {
            docTo.setProjectRegNum("согл-"+ new Random().nextInt(100)+"/19");
        }
        DocTo saved = asDocTo(docRepository.save(createNewDocFromTo(prepareToPersist(docTo))), userName);
        saved.setUrlPDF(getPdfPathByDocTo(saved, rootPath));
        return saved;
    }

    @Override
    public DocTo update(DocTo docTo, int id, String userName, String rootPath) throws NotFoundException, UnauthorizedUserException {
        Assert.notNull(docTo, "docTo must not be null");
        docValuedFieldsRepository.deleteAll(id);
        Doc doc = createNewDocFromTo(prepareToPersist(docTo));
        doc.setId(id);
        doc.setUrlPDF(getPdfPathByDocTo(docTo, rootPath));
        return asDocTo(checkNotFoundWithId(docRepository.save(doc), id), userName);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Assert.notNull(id, "doc must not be null");
        checkNotFoundWithId(docRepository.delete(id)!= 0, id);
    }

    @Override
    public String getPdfPathByDocTo(DocTo docTo, String rootPath) {
        String templatePath = rootPath + docTemplatesDir + "Templ.docx";

        Map<String, String> simpleTags = new HashMap<>();
        simpleTags.put("RegNum", docTo.getRegNum() != null ? docTo.getRegNum() : docTo.getProjectRegNum());
        List<DocFieldsTo> docFieldsTos = docTo.getChildFields();
        List<TableRow> rows = new ArrayList<>();

        for (DocFieldsTo docFieldsTo : docFieldsTos) {
            FieldTo fieldTo = docFieldsTo.getField();
            switch (fieldTo.getFieldType())  {
                case CATALOG:
                    CatalogElem catalogElem= catalogElemRepository.findById(fieldTo.getValueInt()).orElse(null);
                    switch (catalogElem.getCatalog().getCatalogType()) {
                        case TEXT:
                            simpleTags.put(fieldTo.getTag(), catalogElem.getValueStr());
                            break;
                        case NUMBER:
                            simpleTags.put(fieldTo.getTag(), catalogElem.getValueInt());
                            break;
                    }
                    break;
                case GROUP_FIELDS:
                    Map<String, String> rowCells = new HashMap<>();
                    rowCells.put("NumTheme", String.valueOf(rows.size() + 1));
                    StringBuilder commonText = new StringBuilder();
                    for (FieldTo fieldChildTo : fieldTo.getChildFields()) {
                        switch (fieldChildTo.getFieldType()) {
                            case CATALOG:
                                CatalogElem catalogElemChild =
                                        catalogElemRepository.findById(fieldChildTo.getValueInt()).orElse(null);
                                switch (catalogElemChild.getCatalog().getCatalogType()) {
                                    case TEXT:
                                        if (fieldChildTo.getTag().contains(fieldTo.getTag()))
                                            commonText.append(catalogElemChild.getValueStr()).append(" ");
                                        else
                                            rowCells.put(fieldChildTo.getTag(), catalogElemChild.getValueStr());
                                        break;
                                    case NUMBER:
                                        if (fieldChildTo.getTag().contains(fieldTo.getTag()))
                                            commonText.append(catalogElemChild.getValueInt()).append(" ");
                                        else
                                            rowCells.put(fieldChildTo.getTag(), catalogElemChild.getValueInt());
                                        break;
                                }
                                break;
                            case GROUP_FIELDS:
                                break;
                            default:
                                rowCells.put(fieldChildTo.getTag(), fieldChildTo.getValueByFieldType());
                        }
                    }
                    rowCells.put(fieldTo.getTag(), commonText.toString());
                    rows.add(new TableRow(rowCells));
                    break;
                default:
                    simpleTags.put(fieldTo.getTag(), fieldTo.getValueByFieldType());
            }
        }
        TaggedTable taggedTable = new TaggedTable("Table1", rows);

        String pdfTempPath = rootPath + pdfTempDir + "povestka_test1.pdf";
        try {
            Templater.fillTagsByDictionary(templatePath, simpleTags, Arrays.asList(taggedTable),
                    pdfTempPath, true);
        } catch (Exception e) {
            pdfTempPath = null;
        }

        return pdfTempPath.replace(rootPath,"");
    }
}
