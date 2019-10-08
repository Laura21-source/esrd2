package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.DocAgreementRepository;
import ru.gbuac.dao.DocRepository;
import ru.gbuac.model.DocAgreement;
import ru.gbuac.to.DocAgreementTo;
import ru.gbuac.util.exception.NotFoundException;
import java.util.List;
import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DocAgreementServiceImpl implements DocAgreementService {

    @Autowired
    DocAgreementRepository docAgreementRepository;

    @Autowired
    DocRepository docRepository;

    @Override
    public DocAgreement get(int id, int docId) throws NotFoundException {
        DocAgreement docAgreement = docAgreementRepository.findById(id).orElse(null);
        return checkNotFoundWithId(docAgreement != null && docAgreement.getDoc().getId() == docId ? docAgreement : null, id);
    }

    @Override
    public List<DocAgreementTo> getAgreementList(int docId, String userName) {
        return docAgreementRepository.getAgreementList(docId, userName);
    }

    @Override
    public List<DocAgreement> getAll(int docId) {
        return docAgreementRepository.findAll();
    }

    @Override
    public DocAgreement save(DocAgreement docAgreement) {
        Assert.notNull(docAgreement, "docAgreement must not be null");
        return docAgreementRepository.save(docAgreement);
    }

    @Override
    public DocAgreement update(DocAgreement docAgreement, int id) throws NotFoundException {
        Assert.notNull(docAgreement, "docAgreement must not be null");
        return checkNotFoundWithId(docAgreementRepository.save(docAgreement),id);
    }

    @Override
    public void delete(int id, int docId) throws NotFoundException {
        Assert.notNull(id, "docAgreement must not be null");
        checkNotFoundWithId(docAgreementRepository.delete(id, docId) != 0, id);
    }
}
