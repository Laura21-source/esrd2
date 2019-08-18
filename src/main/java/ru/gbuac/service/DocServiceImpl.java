package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.*;
import ru.gbuac.model.*;
import ru.gbuac.to.DocFieldsTo;
import ru.gbuac.to.DocTo;
import ru.gbuac.to.FieldTo;
import ru.gbuac.util.FieldUtil;
import ru.gbuac.util.exception.NotFoundException;

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

    @Override
    public Doc get(int id) throws NotFoundException {
        return checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
    }

    public DocTo asDocTo(Doc doc) {
        List<DocValuedFields> docValuedFields = docValuedFieldsRepository.getAll(doc.getId());
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();

        for (DocValuedFields d:docValuedFields) {
            List<DocTypeFields> docTypeFields = docTypeFieldsRepository.getAll(d.getDoc().getDocType().getId());
            Role role = docTypeFields.stream().filter(f -> f.getField().getId() == d.getValuedField().getField().getId())
                    .findAny().get().getRole();

            docFieldsTos.add(new DocFieldsTo(d.getId(), FieldUtil.asTo(d.getValuedField()),
                    d.getPosition(), role));
        }

        return new DocTo(doc.getId(), doc.getRegNum(), doc.getRegDate(), doc.getInsertDateTime(), doc.getDocType().getId(),
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
        return doc;
    }

    @Override
    public DocTo getFull(int id) throws NotFoundException {
        return asDocTo(checkNotFoundWithId(docRepository.findById(id).orElse(null), id));
    }

    @Override
    public List<Doc> getAll() {
        return docRepository.findAll();
    }

    @Override
    public DocTo save(DocTo docTo) {
        Assert.notNull(docTo, "doc must not be null");
        docTo.setRegNum("согл-"+ new Random().nextInt(100)+"/19");
        return asDocTo(docRepository.save(createNewDocFromTo(docTo)));
    }

    @Override
    public DocTo update(DocTo docTo, int id) throws NotFoundException {
        Assert.notNull(docTo, "docTo must not be null");
        docValuedFieldsRepository.deleteAll(id);
        Doc doc = createNewDocFromTo(docTo);
        doc.setId(id);
        return asDocTo(checkNotFoundWithId(docRepository.save(doc), id));
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Assert.notNull(id, "doc must not be null");
        checkNotFoundWithId(docRepository.delete(id)!= 0, id);
    }
}
