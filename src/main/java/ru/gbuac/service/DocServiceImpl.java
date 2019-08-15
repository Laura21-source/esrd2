package ru.gbuac.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.DocRepository;
import ru.gbuac.model.Doc;
import ru.gbuac.util.exception.NotFoundException;
import java.util.List;
import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DocServiceImpl implements DocService {

    private DocRepository docRepository;

    @Override
    public Doc get(int id) throws NotFoundException {
        return checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
    }

    @Override
    public List<Doc> getAll() {
        return docRepository.findAll();
    }

    @Override
    public Doc save(Doc docType) {
        Assert.notNull(docType, "doc must not be null");
        return docRepository.save(docType);
    }

    @Override
    public Doc update(Doc doc, int id) throws NotFoundException {
        Assert.notNull(doc, "doc must not be null");
        Doc savedDocType = checkNotFoundWithId(docRepository.save(doc), id);
        return savedDocType;
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Assert.notNull(id, "doc must not be null");
        checkNotFoundWithId(docRepository.delete(id)!= 0, id);
    }
}
