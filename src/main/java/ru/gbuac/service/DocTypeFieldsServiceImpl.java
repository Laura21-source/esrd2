package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.AuthorizedUser;
import ru.gbuac.dao.DocTypeFieldsRepository;
import ru.gbuac.dao.RoleRepository;
import ru.gbuac.model.DocTypeFields;
import ru.gbuac.model.Role;
import ru.gbuac.to.DocFieldsTo;
import ru.gbuac.to.FieldTo;
import ru.gbuac.util.FieldUtil;
import ru.gbuac.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DocTypeFieldsServiceImpl implements DocTypeFieldsService {

    @Autowired
    private DocTypeFieldsRepository docTypeFieldsRepository;

    @Autowired
    private RoleRepository roleRepository;

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
        List<Role> curUserRoles = roleRepository.getRolesByUsername(userName);
        List<DocTypeFields> docTypeFields = docTypeFieldsRepository.getAll(docTypeId);
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();

        for (DocTypeFields d:docTypeFields) {
            docFieldsTos.add(new DocFieldsTo(d.getId(), FieldUtil.asTo(d.getField(), curUserRoles),
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
