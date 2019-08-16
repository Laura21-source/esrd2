package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.DocRepository;
import ru.gbuac.dao.DocTypeFieldsRepository;
import ru.gbuac.dao.DocValuedFieldsRepository;
import ru.gbuac.model.Doc;
import ru.gbuac.model.DocTypeFields;
import ru.gbuac.model.DocValuedFields;
import ru.gbuac.model.Role;
import ru.gbuac.to.DocFieldsTo;
import ru.gbuac.to.DocTo;
import ru.gbuac.to.FieldTo;
import ru.gbuac.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private DocRepository docRepository;

    @Autowired
    private DocValuedFieldsRepository docValuedFieldsRepository;

    @Autowired
    private DocTypeFieldsRepository docTypeFieldsRepository;

    @Override
    public Doc get(int id) throws NotFoundException {
        return checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
    }

    @Override
    public DocTo getFull(int id) throws NotFoundException {
        List<DocValuedFields> docValuedFields = docValuedFieldsRepository.getAll(id);
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();

        for (DocValuedFields d:docValuedFields) {
            List<DocTypeFields> docTypeFields = docTypeFieldsRepository.getAll(d.getDoc().getDocType().getId());
            Role role = docTypeFields.stream().filter(f -> f.getField().getId() == d.getValuedField().getField().getId())
                    .findAny().get().getRole();

            docFieldsTos.add(new DocFieldsTo(d.getId(), new FieldTo(d.getValuedField()),
                    d.getPosition(), role));
        }

        Doc doc = checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
        DocTo docTo = new DocTo(doc.getId(), doc.getRegNum(), doc.getRegDate(), doc.getInsertDateTime(), doc.getDocType().getId(),
            docFieldsTos);

        return docTo;
    }

    @Override
    public List<Doc> getAll() {
        return docRepository.findAll();
    }

    @Override
    public DocTo save(DocTo docType) {
        Assert.notNull(docType, "docTo must not be null");
        return null;
    }

    @Override
    public DocTo update(DocTo docTo, int id) throws NotFoundException {
        Assert.notNull(docTo, "docTo must not be null");
        //Doc savedDocType = checkNotFoundWithId(docRepository.save(doc), id);
        return null;
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Assert.notNull(id, "doc must not be null");
        checkNotFoundWithId(docRepository.delete(id)!= 0, id);
    }
}
