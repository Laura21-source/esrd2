package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.AuthorizedUser;
import ru.gbuac.dao.DocTypeFieldsRepository;
import ru.gbuac.dao.FieldsStagesRepository;
import ru.gbuac.dao.RoleRepository;
import ru.gbuac.model.DocTypeFields;
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
public class DocTypeFieldsServiceImpl implements DocTypeFieldsService {

    @Autowired
    private DocTypeFieldsRepository docTypeFieldsRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FieldsStagesRepository fieldsStagesRepository;

    @Override
    public DocTypeFields get(int id, int docTypeId) throws NotFoundException {
        DocTypeFields docTypeFields = docTypeFieldsRepository.findById(id).orElse(null);
        return checkNotFoundWithId(docTypeFields != null && docTypeFields.getDocType().getId() == docTypeId ? docTypeFields : null, id);
    }

    @Override
    public List<DocTypeFields> getAll(int docTypeId) {
        return docTypeFieldsRepository.getAll(docTypeId);
    }

    @Override
    public List<DocFieldsTo> getAllFullByUserName(int docTypeId, String userName) {
        List<String> curUserRoles = AuthorizedUser.getRoles();
        boolean r = AuthorizedUser.hasRole("ADMIN");
        List<DocTypeFields> docTypeFields = docTypeFieldsRepository.getAll(docTypeId);
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();
        List<FieldsStages> fieldsStages = fieldsStagesRepository.getAll(docTypeId);
        Map<Integer, FieldsStages> fMap = fieldsStages.stream().filter(f -> f.getAgreeStage() == 0)
                .collect(Collectors.toMap(FieldsStages::getFieldId, f -> f));

        for (DocTypeFields d:docTypeFields) {
            docFieldsTos.add(new DocFieldsTo(d.getId(), FieldUtil.asTo(d.getField(), curUserRoles, (HashMap<Integer, FieldsStages>) fMap),
                    d.getPosition()));

        }
        return docFieldsTos;
    }

    @Override
    public DocTypeFields save(DocTypeFields docTypeFields) {
        Assert.notNull(docTypeFields, "docTypeFieldsId must not be null");
        return docTypeFieldsRepository.save(docTypeFields);
    }

    @Override
    public DocTypeFields update(DocTypeFields docTypeFields, int id) throws NotFoundException {
        Assert.notNull(docTypeFields, "docTypeFieldsId must not be null");
        return checkNotFoundWithId(docTypeFieldsRepository.save(docTypeFields),id);
    }

    @Override
    public void delete(int id, int docTypeId) throws NotFoundException {
        Assert.notNull(id, "docTypeFields must not be null");
        checkNotFoundWithId(docTypeFieldsRepository.delete(id, docTypeId) != 0, id);
    }
}
