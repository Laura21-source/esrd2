package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.DocAgreementRepository;
import ru.gbuac.dao.DocRepository;
import ru.gbuac.model.Doc;
import ru.gbuac.model.DocAgreement;
import ru.gbuac.to.DocAgreementTo;
import ru.gbuac.util.DocAgreementUtil;
import ru.gbuac.util.exception.NotFoundException;

import java.util.Comparator;
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
    public List<DocAgreementTo> getAgreementList(int docId) {
        return docAgreementRepository.getAgreementList(docId);
    }

    @Override
    public List<DocAgreement> getAll(int docId) {
        return docAgreementRepository.findAll();
    }

    @Override
    public DocAgreementTo save(DocAgreement docAgreement) {
        Assert.notNull(docAgreement, "docAgreement must not be null");
        return DocAgreementUtil.asTo(docAgreementRepository.save(docAgreement));
    }

    @Override
    public DocAgreementTo update(DocAgreement docAgreement, int id) throws NotFoundException {
        Assert.notNull(docAgreement, "docAgreement must not be null");
        return DocAgreementUtil.asTo(checkNotFoundWithId(docAgreementRepository.save(docAgreement),id));
    }

    @Override
    public List<DocAgreementTo> saveList(List<DocAgreement> agreementList, int docId) {
        Doc doc = docRepository.findById(docId).orElse(null);
        agreementList.sort(Comparator.comparing(DocAgreement::getOrdering));
        for (DocAgreement da: agreementList) {
            da.setDoc(doc);
            docAgreementRepository.save(da);
            if (da.getOrdering() == 0) {

            }
        }

        return docAgreementRepository.getAgreementList(docId);
    }

    @Override
    public void delete(int id, int docId) throws NotFoundException {
        Assert.notNull(id, "docAgreement must not be null");
        checkNotFoundWithId(docAgreementRepository.delete(id, docId) != 0, id);
    }
}
