package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.DocTypeFieldsRepository;
import ru.gbuac.model.DocTypeFields;
import ru.gbuac.util.exception.NotFoundException;
import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DocTypeFieldsServiceImpl implements DocTypeFieldsService {

    @Autowired
    private DocTypeFieldsRepository docTypeFieldsRepository;

    @Override
    public DocTypeFields get(int id) throws NotFoundException {
        return checkNotFoundWithId(docTypeFieldsRepository.findById(id).orElse(null), id);
    }

    @Override
    public DocTypeFields save(DocTypeFields docTypeFields) {
        Assert.notNull(docTypeFields, "docTypeFields must not be null");
        return docTypeFieldsRepository.save(docTypeFields);
    }

    @Override
    public DocTypeFields update(DocTypeFields docTypeFields, int id) throws NotFoundException {
        Assert.notNull(docTypeFields, "docTypeFields must not be null");
        DocTypeFields savedDocTypeFields = checkNotFoundWithId(docTypeFieldsRepository.save(docTypeFields), id);
        return savedDocTypeFields;
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Assert.notNull(id, "docTypeFields must not be null");
        checkNotFoundWithId(docTypeFieldsRepository.delete(id)!= 0, id);
    }
}
