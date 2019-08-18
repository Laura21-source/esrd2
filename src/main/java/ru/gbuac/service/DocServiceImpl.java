package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.AuthorizedUser;
import ru.gbuac.dao.*;
import ru.gbuac.model.*;
import ru.gbuac.to.DocFieldsTo;
import ru.gbuac.to.DocTo;
import ru.gbuac.to.FieldTo;
import ru.gbuac.util.FieldUtil;
import ru.gbuac.util.exception.NotFoundException;
import ru.gbuac.util.exception.UnauthorizedUserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Override
    public Doc get(int id) throws NotFoundException {
        return checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
    }

    public DocTo asDocTo(Doc doc, String userName) {
        List<Role> curUserRoles = roleRepository.getRolesByUsername(userName);
        List<DocValuedFields> docValuedFields = docValuedFieldsRepository.getAll(doc.getId());
        List<DocTypeFields> docTypeFields = docTypeFieldsRepository.getAll(doc.getDocType().getId());
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();

        for (DocValuedFields d:docValuedFields) {
            Role role = docTypeFields.stream().filter(f -> f.getField().getId() == d.getValuedField().getField().getId())
                    .findAny().get().getRole();

            docFieldsTos.add(new DocFieldsTo(d.getId(), FieldUtil.asTo(d.getValuedField()),
                    d.getPosition(), curUserRoles.contains(role)));
        }

        Integer agreementDocId = null;
        if (doc.getAgreementDoc() != null) {
            agreementDocId = doc.getAgreementDoc().getId();
        }

        return new DocTo(doc.getId(), doc.getRegNum(), doc.getRegDate(), doc.getInsertDateTime(),
                doc.getDocType().getId(), agreementDocId, docFieldsTos);
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

    public Doc createNewDocFromTo(DocTo docTo) {
        DocType docType = docTypeRepository.findById(docTo.getDocTypeId()).orElse(null);
        Doc doc = new Doc(null, docTo.getRegNum(), docTo.getRegDate(), docTo.getInsertDateTime(),
                docType, null);
        List<DocFieldsTo> docFieldsTos = docTo.getChildFields();
        List<DocValuedFields> docValuedFields = new ArrayList<>();

        for (DocFieldsTo d:docFieldsTos) {
            docValuedFields.add(new DocValuedFields(null, doc,
                    createNewValueFieldFromTo(d.getField()), d.getPosition()));
        }
        doc.setDocValuedFields(docValuedFields);
        if (docTo.getAgreementDocId() == null) {
            doc.setCurrentAgreementStage(1);
        }
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

    @Override
    public DocTo save(DocTo docTo, String userName) throws UnauthorizedUserException {
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

        if (docTo.getAgreementDocId() == null) {
            docTo.setRegNum("согл-"+ new Random().nextInt(100)+"/19");
        }
        else
            docTo.setRegNum("ДЭПР-"+ new Random().nextInt(100)+"/19");
        return asDocTo(docRepository.save(createNewDocFromTo(docTo)), userName);
    }

    @Override
    public DocTo update(DocTo docTo, int id, String userName) throws NotFoundException, UnauthorizedUserException {
        Assert.notNull(docTo, "docTo must not be null");
        docValuedFieldsRepository.deleteAll(id);
        Doc doc = createNewDocFromTo(docTo);
        doc.setId(id);
        return asDocTo(checkNotFoundWithId(docRepository.save(doc), id), userName);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Assert.notNull(id, "doc must not be null");
        checkNotFoundWithId(docRepository.delete(id)!= 0, id);
    }
}
