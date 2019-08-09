package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.DocTypeRepository;
import ru.gbuac.model.DocType;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DocTypeServiceImpl implements DocTypeService{

    @Autowired
    private DocTypeRepository docTypeRepository;

    @Override
    public DocType get(int id) throws NotFoundException {
        return checkNotFoundWithId(docTypeRepository.findById(id).orElse(null), id);
    }

    @Override
    public List<DocType> getAll() {
        return docTypeRepository.findAll();
    }

    @Override
    public DocType save(DocType docType) {
        Assert.notNull(docType, "docType must not be null");
        return docTypeRepository.save(docType);
    }

    @Override
    public DocType update(DocType docType, int id) throws NotFoundException {
        Assert.notNull(docType, "docType must not be null");
        DocType savedDocType = checkNotFoundWithId(docTypeRepository.save(docType), id);
        return savedDocType;
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Assert.notNull(id, "docType must not be null");
        checkNotFoundWithId(docTypeRepository.delete(id)!= 0, id);
    }
}
