package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.DocTypeFieldsRepository;
import ru.gbuac.dao.DocValuedFieldsRepository;
import ru.gbuac.model.DocTypeFields;
import ru.gbuac.model.DocValuedFields;
import ru.gbuac.to.DocTypeFieldsTo;
import ru.gbuac.to.FieldTo;
import ru.gbuac.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DocValuedFieldsServiceImpl implements DocValuedFieldsService {

    @Autowired
    private DocValuedFieldsRepository docValuedFieldsRepository;

    @Override
    public DocValuedFields get(int id, int docId) throws NotFoundException {
        DocValuedFields docValuedFields = docValuedFieldsRepository.findById(id).orElse(null);
        return checkNotFoundWithId(docValuedFields != null && docValuedFields.getDoc().getId() == docId ? docValuedFields : null, id);
    }

    @Override
    public List<DocValuedFields> getAll(int docId) {
        return docValuedFieldsRepository.getAll(docId);
    }

    @Override
    public DocValuedFields save(DocValuedFields docValuedFields) {
        Assert.notNull(docValuedFields, "docValuedFields must not be null");
        return docValuedFieldsRepository.save(docValuedFields);
    }

    @Override
    public DocValuedFields update(DocValuedFields docValuedFields, int id) throws NotFoundException {
        Assert.notNull(docValuedFields, "docValuedFields must not be null");
        return checkNotFoundWithId(docValuedFieldsRepository.save(docValuedFields),id);
    }

    @Override
    public void delete(int id, int docId) throws NotFoundException {
        Assert.notNull(id, "docValuedFields must not be null");
        checkNotFoundWithId(docValuedFieldsRepository.delete(id, docId) != 0, id);
    }
}