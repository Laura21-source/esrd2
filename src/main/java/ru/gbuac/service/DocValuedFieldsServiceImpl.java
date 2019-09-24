package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.AuthorizedUser;
import ru.gbuac.dao.DocRepository;
import ru.gbuac.dao.DocTypeRepository;
import ru.gbuac.dao.DocValuedFieldsRepository;
import ru.gbuac.dao.FieldsStagesRepository;
import ru.gbuac.model.DocValuedFields;
import ru.gbuac.model.FieldsStages;
import ru.gbuac.to.DocFieldsTo;
import ru.gbuac.util.FieldUtil;
import ru.gbuac.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DocValuedFieldsServiceImpl implements DocValuedFieldsService {

    @Autowired
    private DocValuedFieldsRepository docValuedFieldsRepository;

    @Autowired
    private FieldsStagesRepository fieldsStagesRepository;

    @Autowired
    private DocRepository docRepository;

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
    public List<DocFieldsTo> getAllFull(int docId, String userName) {
        List<String> curUserRoles = AuthorizedUser.getRoles();
        int docTypeId = docRepository.getDocTypeByDocId(docId);
        List<DocValuedFields> docValuedFields = docValuedFieldsRepository.getAll(docId);
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();
        List<FieldsStages> fieldsStages = fieldsStagesRepository.getAll(docTypeId);
        Map<Integer, FieldsStages> fMap = fieldsStages.stream().filter(f -> f.getAgreeStage() == 0)
                .collect(Collectors.toMap(FieldsStages::getFieldId, f -> f));

        for (DocValuedFields d:docValuedFields) {
            docFieldsTos.add(new DocFieldsTo(d.getId(), FieldUtil.asTo(d.getValuedField(), curUserRoles, (HashMap<Integer, FieldsStages>) fMap),
                    d.getPosition()));
        }
        return docFieldsTos;
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
