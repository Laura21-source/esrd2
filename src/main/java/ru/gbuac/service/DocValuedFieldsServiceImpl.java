package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.AuthorizedUser;
import ru.gbuac.dao.*;
import ru.gbuac.model.*;
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
    private DocTypeFieldsRepository docTypeFieldsRepository;

    @Autowired
    private FieldsRolesRepository fieldsRolesRepository;

    @Autowired
    private DocRepository docRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

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
        List<FieldsRoles> fieldsRoles = fieldsRolesRepository.getAll(docTypeId);
        Map<Integer, FieldsRoles> fMap = fieldsRoles.stream()
                .collect(Collectors.toMap(FieldsRoles::getFieldId, f -> f));

        DocStatus docStatus = docRepository.getDocStatusByDocId(docId);
        boolean deny = false;
        if (docStatus == DocStatus.IN_WORK) {
            deny = true;
        }

        for (DocValuedFields d:docValuedFields) {
            docFieldsTos.add(new DocFieldsTo(d.getId(),
                    FieldUtil.asTo(d.getValuedField(), curUserRoles, (HashMap<Integer, FieldsRoles>) fMap, deny),
                    d.getPosition()));
        }
        return docFieldsTos;
    }

    @Override
    public List<DocFieldsTo> getAllMerged(int docId, int targetDocTypeId, String userName) {
        List<String> curUserRoles = AuthorizedUser.getRoles();

        List<DocValuedFields> docValuedFields = docValuedFieldsRepository.getAll(docId);
        List<DocTypeFields> docTypeFields = docTypeFieldsRepository.getAll(targetDocTypeId);
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();
        List<FieldsRoles> fieldsRoles = fieldsRolesRepository.getAll(targetDocTypeId);
        Map<Integer, FieldsRoles> fMap = fieldsRoles.stream()
                .collect(Collectors.toMap(FieldsRoles::getFieldId, f -> f));

        User thisUser = userRepository.getByName(userName);
        Doc curDoc = docRepository.findById(docId).orElse(null);
        boolean thisUserIsExecutor = curDoc.getExecutorUsers().stream()
                .map(User::getId).collect(Collectors.toList()).contains(thisUser.getId());
        boolean deny = false;
        if (!thisUserIsExecutor && !AuthorizedUser.hasRole("ADMIN")) {
            deny = true;
        }

        for (DocTypeFields t:docTypeFields) {
            boolean hasField = false;
            for (DocValuedFields v:docValuedFields) {
                if (t.getField().getId().equals(v.getValuedField().getField().getId()))
                {
                    v.getValuedField().setId(null);
                    docFieldsTos.add(new DocFieldsTo(null, FieldUtil.asTo(v.getValuedField(), curUserRoles, (HashMap<Integer, FieldsRoles>) fMap, deny),
                            v.getPosition()));
                    hasField = true;
                    break;
                }
            }
            if (!hasField) {
                docFieldsTos.add(new DocFieldsTo(null, FieldUtil.asTo(t.getField(), curUserRoles, (HashMap<Integer, FieldsRoles>) fMap, deny),
                        t.getPosition()));
            }

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
