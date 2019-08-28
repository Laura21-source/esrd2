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
import ru.gbuac.to.PdfTo;
import ru.gbuac.util.*;
import ru.gbuac.util.exception.NotFoundException;
import ru.gbuac.util.exception.UnauthorizedUserException;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
    private String pdfDir;

    @Value("${pdf.temp.dir}")
    private String pdfTempDir;

    @Value("${doc.templates.dir}")
    private String docTemplatesDir;

    @Override
    public Doc get(int id) throws NotFoundException {
        return checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
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

    private Doc prepareToPersist(Doc doc) {
        Integer currentAgreementStage = doc.getCurrentAgreementStage();
        Integer finalStageForThisDocType = docTypeRoutesRepository.getFinalStageForDocType(doc.getDocType().getId());
        if (doc.getRegNum() == null) {
            if (currentAgreementStage == null) {
                doc.setCurrentAgreementStage(1);
            } else {
                if (currentAgreementStage != finalStageForThisDocType)
                    doc.setCurrentAgreementStage(currentAgreementStage + 1);
            }
        }
        return doc;
    }

    @Override
    public DocTo save(DocTo docTo, String userName, String rootPath) throws UnauthorizedUserException {
        Assert.notNull(docTo, "doc must not be null");
        List<DocTypeFields> docTypeFields = docTypeFieldsRepository.getAll(docTo.getDocTypeId());

        long existRole = 0L;
        for (DocFieldsTo d:docTo.getChildFields()) {
            existRole += docTypeFields.stream()
                    .filter(f -> f.getField().getId().equals(d.getField().getFieldId())).count();
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
        DocTo saved = asDocTo(docRepository.save(prepareToPersist(createNewDocFromTo(docTo))), userName);
        String urlPdf = createPdf(saved, rootPath, false);
        docRepository.setUrlPDF(saved.getId(), urlPdf);
        saved.setUrlPDF(urlPdf);
        return saved;
    }

    @Override
    public DocTo update(DocTo docTo, int id, String userName, String rootPath) throws NotFoundException, UnauthorizedUserException {
        Assert.notNull(docTo, "docTo must not be null");
        Doc updated = docFromTo(docTo);
        docValuedFieldsRepository.deleteAll(id);
        DocTo updatedTo = asDocTo(checkNotFoundWithId(docRepository.save(prepareToPersist(updated)), id), userName);
        updatedTo.setUrlPDF(createPdf(updatedTo, rootPath, false));
        return updatedTo;
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Assert.notNull(id, "doc must not be null");
        checkNotFoundWithId(docRepository.delete(id)!= 0, id);
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
                default:
                    cellsTags.put(tag, fieldTo.getValueByFieldType());
                    break;
            }
        } else
        if (TagUtil.getSimpleTag(tag) != null) {
            switch (fieldTo.getFieldType()) {
                case GROUP_FIELDS:
                    for (FieldTo childField : fieldTo.getChildFields()) {
                        fillTags(childField, simpleTags, taggedTables, fieldTo.getChildFields().size());
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
                default:
                    simpleTags.put(tag, fieldTo.getValueByFieldType());
                    break;
            }
        }

    }

    @Override
    public PdfTo getPdfPathByDocTo(DocTo docTo, String rootPath) {
        return new PdfTo(createPdf(asDocTo(docFromTo(docTo), null), rootPath, true));
    }

    private String createPdf(DocTo docTo, String rootPath, Boolean saveToTempDir) {
        String templatePath = rootPath + docTemplatesDir + "Templ.docx";
        String pdfTempPath = rootPath + pdfTempDir + docTo.getId() + ".pdf";
        String pdfPath = rootPath + pdfDir + docTo.getId() + ".pdf";
        String savePath = saveToTempDir ? pdfTempPath : pdfPath;

        Map<String, String> simpleTags = new HashMap<>();
        simpleTags.put("RegNum", docTo.getRegNum() != null ? docTo.getRegNum() : docTo.getProjectRegNum());
        Map<String, TaggedTable> taggedTables = new HashMap<>();

        for (DocFieldsTo docFieldsTo : docTo.getChildFields()) {
            fillTags(docFieldsTo.getField(), simpleTags, taggedTables, docTo.getChildFields().size());
        }

        try {
            ByteArrayOutputStream byteArrayOutputStream =
                    Templater.fillTagsByDictionary(templatePath, simpleTags, taggedTables,
                    pdfTempPath, true);
            byteArrayOutputStream.writeTo(new FileOutputStream(savePath));
        } catch (Exception e) {
        }

        return savePath.replace(rootPath,"");
    }

    private DocTo asDocTo(Doc doc, String userName) {
        Integer docTypeId = doc.getDocType().getId();

        Integer curAgreementStage = doc.getCurrentAgreementStage();
        Boolean isFinalStage = docTypeRoutesRepository.getFinalStageForDocType(docTypeId) == curAgreementStage;

        List<Role> curUserRoles = RolesUtil.getPlainList(roleRepository.getRolesByUsername(userName));
        List<DocValuedFields> docValuedFields = doc.getDocValuedFields();
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();

        for (DocValuedFields d:docValuedFields) {
            docFieldsTos.add(new DocFieldsTo(d.getId(), FieldUtil.asTo(d.getValuedField(), curUserRoles),
                    d.getPosition()));
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
