package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.AuthorizedUser;
import ru.gbuac.dao.*;
import ru.gbuac.model.*;
import ru.gbuac.to.DocFieldsTo;
import ru.gbuac.to.FieldTo;
import ru.gbuac.util.DocTypeFieldsUtil;
import ru.gbuac.util.DocValuedFieldsUtil;
import ru.gbuac.util.FieldUtil;
import ru.gbuac.util.exception.NotFoundException;

import java.util.*;
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
        List<FieldsRoles> fieldsRoles = fieldsRolesRepository.getAll(docTypeId);
        Map<Integer, FieldsRoles> fMap = fieldsRoles.stream()
                .collect(Collectors.toMap(FieldsRoles::getFieldId, f -> f));

        DocStatus docStatus = docRepository.getDocStatusByDocId(docId);
        boolean deny = false;
        if (docStatus == DocStatus.IN_WORK) {
            deny = true;
        }

        return DocValuedFieldsUtil.asTo(docValuedFields, curUserRoles, fMap, deny, false);
    }

    @Override
    public List<DocFieldsTo> getAllMerged(int docId, int targetDocTypeId, List<Integer> optionalDocIds, String userName) {
        List<String> curUserRoles = AuthorizedUser.getRoles();

        List<DocValuedFields> docValuedFields = docValuedFieldsRepository.getAll(docId);
        List<DocTypeFields> docTypeFields = docTypeFieldsRepository.getAll(targetDocTypeId);
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();
        List<FieldsRoles> fieldsRoles = fieldsRolesRepository.getAll(targetDocTypeId);
        Map<Integer, FieldsRoles> fMap = fieldsRoles.stream()
                .collect(Collectors.toMap(FieldsRoles::getFieldId, f -> f));

        User thisUser = userRepository.getByName(userName);
        Doc curDoc = docRepository.findById(docId).orElse(null);
        boolean thisUserIsExecutor = curDoc.getResolutions().stream()
                .flatMap(r -> r.getResolutionsUsers().stream())
                .map(ru -> ru.getUser().getId()).collect(Collectors.toList()).contains(thisUser.getId());
        boolean deny = false;
        if (!thisUserIsExecutor && !AuthorizedUser.hasRole("ADMIN")) {
            deny = true;
        }

        List<DocFieldsTo> templateFields = DocTypeFieldsUtil.asTo(docTypeFields, curUserRoles, fMap, deny);
        List<DocFieldsTo> valuedFields = DocValuedFieldsUtil.asTo(docValuedFields, curUserRoles, fMap, deny, true);
        List<DocFieldsTo> resultFields = new ArrayList<>();
        for (DocFieldsTo t: templateFields) {
            if (t.getField().getFieldType().equals(FieldType.GROUP_FIELDS)) {
                List<DocFieldsTo> v = valuedFields.stream()
                        .filter(f -> f.getField().getTag().equals(t.getField().getTag())).collect(Collectors.toList());
                for (DocFieldsTo subV: v) {
                    FieldTo newTo = null;
                    for (FieldTo templateField: t.getField().getChildFields()) {
                        FieldTo valuedField = subV.getField().getChildFields().stream()
                                .filter(f -> f.getTag().equals(templateField.getTag())).findFirst().orElse(null);
                        newTo = new FieldTo(null, templateField.getName(), templateField.getChildFields(), templateField.getId(),
                                templateField.getFieldType(), templateField.getPositionInGroup(), templateField.getMaxCount(),
                                templateField.getLength(), templateField.getParentCatalogId(), templateField.getCatalogId(),
                                templateField.getEnabled(), templateField.getRequired(), templateField.getAppendix(),
                                templateField.getTag(), templateField.getAddImage(), templateField.getImagePath());
                        if (valuedField != null) {
                            newTo.setValueStr(valuedField.getValueStr());
                            newTo.setValueInt(valuedField.getValueInt());
                            newTo.setValueDate(valuedField.getValueDate());
                        }
                    }
                    resultFields.add(new DocFieldsTo(null, newTo, t.getPosition()));
                }
            } else {
                DocFieldsTo v = valuedFields.stream()
                        .filter(f -> f.getField().getTag().equals(t.getField().getTag())).findFirst().orElse(null);
                if (v != null) {
                    resultFields.add(v);
                } else {
                    resultFields.add(t);
                }
            }
        }
        resultFields.sort(Comparator.comparing(DocFieldsTo::getPosition));
        return resultFields;
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
