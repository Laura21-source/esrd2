package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ru.gbuac.AuthorizedUser;
import ru.gbuac.dao.*;
import ru.gbuac.model.*;
import ru.gbuac.to.*;
import ru.gbuac.util.*;
import ru.gbuac.util.exception.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private DocRepository docRepository;

    @Autowired
    private DocValuedFieldsRepository docValuedFieldsRepository;

    @Autowired
    private DocTypeFieldsRepository docTypeFieldsRepository;

    @Autowired
    private DocTypeRepository docTypeRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private CatalogElemRepository catalogElemRepository;

    @Autowired
    private DocAgreementRepository docAgreementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FieldsRolesRepository fieldsRolesRepository;

    @Autowired
    private DocNumberPrefixesRepository docNumberPrefixesRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ResolutionRepository resolutionRepository;

    @Autowired
    MailService mailService;

    @Value("${pdf.final.dir}")
    private String pdfDir;

    @Value("${pdf.temp.dir}")
    private String pdfTempDir;

    @Value("${docx.temp.dir}")
    private String docxTempDir;

    @Value("${doc.templates.dir}")
    private String docTemplatesDir;

    @Value("${uploads.temp.dir}")
    private String uploadsTempDir;

    @Value("${uploads.dir}")
    private String uploadsDir;

    private final int DEADLINE_DAYS = 3;

    private boolean isFinalAgreementStage(int docId) {
        List<Boolean> stages = docAgreementRepository.isFinalAgreementStage(docId, PageRequest.of(0,1));
        return stages.isEmpty() ? false : stages.get(0);
    }

    @Override
    public Doc get(int id) throws NotFoundException {
        return checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
    }

    @Override
    public DocTo getFullByUserName(int id, String userName) throws NotFoundException {
        DocTo docTo = asDocTo(checkNotFoundWithId(docRepository.findById(id).orElse(null), id));
        DocStatus docStatus = docTo.getDocStatus();
        if (!docStatus.equals(DocStatus.IN_AGREEMENT))  {
            docTo.setCanAgree(false);
        } else {
            boolean hasRights = docAgreementRepository.isTimeForAgreeForUser(docTo.getId(), userName).orElse(false);
            docTo.setCanAgree(hasRights || AuthorizedUser.hasRole("ADMIN"));
        }
        boolean isFinalAgreementStage = isFinalAgreementStage(id);
        if (!isFinalAgreementStage) {
            docTo.setFinalStage(false);
        } else {
            docTo.setFinalStage(true);
        }
        if (docStatus.equals(DocStatus.IN_WORK)) {
            User thisUser = userRepository.getByName(userName);

            boolean thisUserIsExecutor = docTo.getExecutorUsersIds().contains(thisUser.getId());
            docTo.setCanWork(thisUserIsExecutor || AuthorizedUser.hasRole("ADMIN"));

            boolean thisUserCanDistribute = thisUser.getDistributionDepartments().stream()
                    .anyMatch(ud -> docTo.getExecutorDepartmentsIds().contains(ud.getId()));
            docTo.setCanDistribute(thisUserCanDistribute || AuthorizedUser.hasRole("ADMIN"));
        } else {
            docTo.setCanWork(false);
        }


        return docTo;
    }

    @Override
    public List<Doc> getAllAgreementByUsername(String userName) {
        return docRepository.getAllAgreementByUserName(userName);
    }

    @Override
    public List<Doc> getAllAgreementMoreDeadlineByUserName(String userName) {
        return docRepository.getAllAgreementMoreDeadline(userName, -1, LocalDate.now().plusDays(DEADLINE_DAYS));
    }

    @Override
    public List<Doc> getAllAgreementLessDeadlineByUserName(String userName) {
        return docRepository.getAllAgreementLessDeadline(userName, -1, LocalDate.now().plusDays(DEADLINE_DAYS));
    }

    @Override
    public List<Doc> getAllAgreementMoreDeadlineByDepartment(String userName) {
        Department department = departmentRepository.getByUserName(userName).orElse(null);
        if (department != null && !department.isTopLevel()) {
            department = departmentRepository.getTopLevelForDepartment(department.getId()).orElse(null);
        }
        if (department != null) {
            List<Doc> docs =
                    docRepository.getAllAgreementMoreDeadline("", department.getId(), LocalDate.now().plusDays(DEADLINE_DAYS));
            return docs;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Doc> getAllAgreementLessDeadlineByDepartment(String userName) {
        Department department = departmentRepository.getByUserName(userName).orElse(null);
        if (department != null && !department.isTopLevel()) {
            department = departmentRepository.getTopLevelForDepartment(department.getId()).orElse(null);
        }
        if (department != null) {
            List<Doc> docs =
                    docRepository.getAllAgreementLessDeadline("", department.getId(), LocalDate.now().plusDays(DEADLINE_DAYS));
            return docs;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Doc> getAllAgreedByUsername(String userName) {
        return docRepository.getAllAgreedByUserName(userName);
    }

    @Override
    public List<Doc> getAllRegisteredByUsername(String userName) {
        return docRepository.getAllRegisteredByUserName(userName);
    }

    @Override
    public List<DocItemTo> getAll() {
        List<DocItemTo> docItemsTo = getWithUserDepsExecutors(docRepository.getAll());
        for (DocItemTo d: docItemsTo) {
            UserTo current = docAgreementRepository.getCurrentUser(d.getId());
            d.setCurrentAgreeFullName(current != null ? current.getFullName() : "");
        }
        return docItemsTo;
    }

    @Override
    public List<Doc> getAllAgreement() {
        return docRepository.getAllAgreement();
    }

    @Override
    public List<Doc> getAllRegistered() {
        return docRepository.getAllRegistered();
    }

    private List<DocItemTo> getWithUserDepsExecutors(List<Doc> docs) {
        List<DocItemTo> docItemsTo = new ArrayList<>();
        for (Doc d: docs) {
            StringBuilder deps = new StringBuilder();
            deps.append("[");
            StringBuilder users = new StringBuilder();
            users.append("[");
            for (Resolution resolution : d.getResolutions()) {
                Department department = resolution.getDepartment();
                deps.append("{\"id\":" + department.getId()+", \"name\":\""+ department.getName() + "\"},");

                for (ResolutionsUsers ru: resolution.getResolutionsUsers()) {
                    users.append("{\"id\":" + ru.getUser().getId()+", \"fullName\":\""+ ru.getUser().getLastname() + " "
                            + ru.getUser().getFirstname() + " " + ru.getUser().getPatronym() + "\"},");
                }
            }

            if (!d.getResolutions().isEmpty() && deps.charAt(deps.length()-1) == ',') {
                deps.delete(deps.length()-1, deps.length());
            }
            deps.append("]");

            if (users.length() > 1 && users.charAt(users.length()-1) == ',') {
                users.delete(users.length()-1, users.length());
            }
            users.append("]");

            LocalDate controlDate = Optional.ofNullable(d.getResolutions())
                    .map(Collection::stream).orElseGet(Stream::empty).filter(r -> r.isPrimaryResolution())
                    .map(r -> Optional.ofNullable(r.getControlDate())).findFirst().orElse(null).orElse(null);
            boolean isAlarmControlDate = false;
            if (controlDate != null) {
                isAlarmControlDate = controlDate.isBefore(LocalDate.now().plusDays(DEADLINE_DAYS+1));
            }
            docItemsTo.add(new DocItemTo(d.getId(), d.getDocStatus(), d.getRegNum(), d.getRegDateTime(),
                    d.getProjectRegNum(), d.getProjectRegDateTime(), controlDate, isAlarmControlDate, deps.toString(),
                    users.toString(), d.getDocType().getName()));
        }
        return docItemsTo;
    }

    @Override
    public List<DocItemTo> getAllInWorkByUserName(String userName) {
        List<Doc> docs = docRepository.getAllInWorkByUserName(userName);
        return getWithUserDepsExecutors(docs);
    }

    @Override
    public List<DocItemTo> getAllInWorkMoreDeadlineByUserName(String userName) {
        departmentRepository.getByUserName(userName);
        List<Doc> docs = docRepository.getAllInWorkMoreDeadline(userName, -1, LocalDate.now().plusDays(DEADLINE_DAYS));
        return getWithUserDepsExecutors(docs);
    }

    @Override
    public List<DocItemTo> getAllInWorkLessDeadlineByUserName(String userName) {
        List<Doc> docs = docRepository.getAllInWorkLessDeadline(userName, -1, LocalDate.now().plusDays(DEADLINE_DAYS));
        return getWithUserDepsExecutors(docs);
    }

    @Override
    public List<DocItemTo> getAllInWorkMoreDeadlineByDepartment(String userName) {
        Department department = departmentRepository.getByUserName(userName).orElse(null);
        if (department != null && !department.isTopLevel()) {
            department = departmentRepository.getTopLevelForDepartment(department.getId()).orElse(null);
        }
        if (department != null) {
            List<Doc> docs =
                    docRepository.getAllInWorkMoreDeadline("", department.getId(), LocalDate.now().plusDays(DEADLINE_DAYS));
            return getWithUserDepsExecutors(docs);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<DocItemTo> getAllInWorkLessDeadlineByDepartment(String userName) {
        Department department = departmentRepository.getByUserName(userName).orElse(null);
        if (department != null && !department.isTopLevel()) {
            department = departmentRepository.getTopLevelForDepartment(department.getId()).orElse(null);
        }
        if (department != null) {
            List<Doc> docs =
                    docRepository.getAllInWorkLessDeadline("", department.getId(), LocalDate.now().plusDays(DEADLINE_DAYS));
            return getWithUserDepsExecutors(docs);
        } else {
            return Collections.emptyList();
        }
    }

    private List<Department> getUserDistributionDepartments(String userName) {
        User curUser = userRepository.getByName(userName);
        return curUser.getDistributionDepartments();
    }

    @Override
    public List<DocItemTo> getAllDistribution(String userName) {
        List<Doc> docs = new ArrayList<>();
        Optional.ofNullable(getUserDistributionDepartments(userName)).map(Collection::stream).orElseGet(Stream::empty)
                .forEach(d -> docs.addAll(docRepository.getAllDistribution(d.getId())));
        return getWithUserDepsExecutors(docs);
    }

    @Override
    public List<DocItemTo> getAllDistributionMoreDeadlineByChiefUserName(String userName) {
        List<Doc> docs = new ArrayList<>();
        Optional.ofNullable(getUserDistributionDepartments(userName)).map(Collection::stream).orElseGet(Stream::empty)
                .forEach(d -> docs.addAll(docRepository.getAllDistributionMoreDeadline(d.getId(), LocalDate.now().plusDays(DEADLINE_DAYS))));
        return getWithUserDepsExecutors(docs);
    }

    @Override
    public List<DocItemTo> getAllDistributionLessDeadlineByChiefUserName(String userName) {
        List<Doc> docs = new ArrayList<>();
        Optional.ofNullable(getUserDistributionDepartments(userName)).map(Collection::stream).orElseGet(Stream::empty)
                .forEach(d -> docs.addAll(docRepository.getAllDistributionLessDeadline(d.getId(), LocalDate.now().plusDays(DEADLINE_DAYS))));
        return getWithUserDepsExecutors(docs);
    }

    @Override
    public List<DocItemTo> getAllDistributionMoreDeadlineByDepartment(String userName) {
        Department department = departmentRepository.getByUserName(userName).orElse(null);
        if (department != null && !department.isTopLevel()) {
            department = departmentRepository.getTopLevelForDepartment(department.getId()).orElse(null);
        }
        if (department != null) {
            List<Doc> docs = docRepository.getAllDistributionMoreDeadline(department.getId(), LocalDate.now().plusDays(DEADLINE_DAYS));
            return getWithUserDepsExecutors(docs);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<DocItemTo> getAllDistributionLessDeadlineByDepartment(String userName) {
        Department department = departmentRepository.getByUserName(userName).orElse(null);
        if (department != null && !department.isTopLevel()) {
            department = departmentRepository.getTopLevelForDepartment(department.getId()).orElse(null);
        }
        if (department != null) {
            List<Doc> docs = docRepository.getAllDistributionLessDeadline(department.getId(), LocalDate.now().plusDays(DEADLINE_DAYS));
            return getWithUserDepsExecutors(docs);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<DocItemTo> getAllDistributed(String userName) {
        List<Doc> docs = new ArrayList<>();
        Optional.ofNullable(getUserDistributionDepartments(userName)).map(Collection::stream).orElseGet(Stream::empty)
                .forEach(d -> docs.addAll(docRepository.getAllDistributed(d.getId())));
        return getWithUserDepsExecutors(docs);
    }

    @Override
    public List<DocItemTo> getAllAtThisMounthOnControl(String userName) {
        List<Doc> docs = docRepository.getAllAtThisMounthOnControl(userName, LocalDate.now(), LocalDate.now().minusMonths(1));
        return getWithUserDepsExecutors(docs);
    }

    @Override
    public List<DocItemTo> getAllAtThisMounthOnControlCompletedInTime(String userName) {
        List<Doc> docs =  docRepository.getAllAtThisMounthOnControlCompletedInTime(userName, LocalDate.now(), LocalDate.now().minusMonths(1));
        return getWithUserDepsExecutors(docs);
    }

    @Override
    public List<DocItemTo> getAllAtThisMounthOnControlCompletedAfterTime(String userName) {
        List<Doc> docs =  docRepository.getAllAtThisMounthOnControlCompletedAfterTime(userName, LocalDate.now(), LocalDate.now().minusMonths(1));
        return getWithUserDepsExecutors(docs);
    }

    @Override
    public List<DocItemTo> getAllAtThisMounthOnControlNotCompleted(String userName) {
        List<Doc> docs =  docRepository.getAllAtThisMounthOnControlNotCompleted(userName, LocalDate.now());
        return getWithUserDepsExecutors(docs);
    }

    @Override
    public List<DocNumberTo> getRegNumbers() {
        return docRepository.getRegNumbers();
    }

    @Override
    public DocTo save(DocTo docTo, String userName, String rootPath) throws UnauthorizedUserException {
        Assert.notNull(docTo, "doc must not be null");

        DocType docType = docTypeRepository.findById(docTo.getDocTypeId()).orElse(null);
        if (!docType.isFinalDoc()) {
            if (docTo.getExecutorDepartmentsIds() == null || docTo.getExecutorDepartmentsIds().isEmpty()) {
                throw new EmptyToException();
            }
        }

        Integer finalUserId = docTo.getFinalUserId();
        boolean hasRights = AuthorizedUser.hasRole(docType.getRole().getAuthority());
        if (!hasRights && !AuthorizedUser.hasRole("ADMIN")) {
            throw new UnauthorizedUserException();
        }

        docTo.setProjectRegNum(docNumberPrefixesRepository.generateDocNumber("согл", ""));
        Doc docToSave = createNewDocFromTo(docTo);
        docToSave.setInitialUser(userRepository.getByName(userName));

        Doc saved = docRepository.save(docToSave);
        DocTo savedTo = asDocTo(saved);
        savedTo.setFinalUserId(finalUserId);
        String urlPdf = createPDFOrDocx(savedTo, rootPath, false, true);
        docRepository.setUrlPDF(savedTo.getId(), urlPdf);
        savedTo.setUrlPDF(urlPdf);
        savedTo.setCanAgree(hasRights);
        return savedTo;
    }

    @Override
    public DocTo update(DocTo docTo, int id, String userName, String rootPath) throws NotFoundException, UnauthorizedUserException {
        String comment = docTo.getComment();
        Assert.notNull(docTo, "docTo must not be null");

        Doc updated = docFromTo(docTo);
        boolean isFinalAgreementStage = isFinalAgreementStage(id);

        if (isFinalAgreementStage) {
            String docTypeMask = docNumberPrefixesRepository.getMaskByDocTypeId(docTo.getDocTypeId());
            String docNumber = "";
            if (docTo.getDocTypeId() == 2) {
                List<Doc> docs = docRepository.getAllByDocType(docTo.getDocTypeId());
                StringBuilder optional = new StringBuilder();
                for (DocValuedFields docValuedFields : updated.getDocValuedFields()) {
                    if (docValuedFields.getValuedField().getField().getId() == 4) {
                        LocalDate agendaDate = docValuedFields.getValuedField().getValueDate().toLocalDate();
                        optional.append(agendaDate.format(DateTimeFormatter.ofPattern("dd.MM")));
                        long agendaCountForDay = docs.stream()
                                .flatMap(f -> f.getDocValuedFields().stream())
                                .filter(f -> f.getValuedField().getField().getId() == 4)
                                .filter(f -> f.getValuedField().getValueDate().toLocalDate().equals(agendaDate))
                                .count();
                        optional.append("-");
                        optional.append(agendaCountForDay);
                        break;
                    }
                }
                docNumber = docNumberPrefixesRepository.generateDocNumber(docTypeMask, optional.toString());
            } else {
                docNumber = docNumberPrefixesRepository.generateDocNumber(docTypeMask, "");
            }
            updated.setRegNum(docNumber);
            updated.setRegDateTime(LocalDateTime.now());
            updated.setDocStatus(DocStatus.IN_WORK);
            resolutionRepository
                    .setControlDateForPrimaryResolution(updated.getId(), LocalDateTime.now().plusDays(2).toLocalDate());
        }

        boolean hasRights = docAgreementRepository.isTimeForAgreeForUser(docTo.getId(), userName).orElse(false);
        if (!hasRights && !AuthorizedUser.hasRole("ADMIN")) {
            throw new UnauthorizedUserException();
        }

        // Генерация проектного PDF с добавлением ссылки в сущность, либо если isFinalStage, то
        // перемещаем файл из временного хранилища temp_uploads в хранилище pdf
        updated.setUrlPDF(createPDFOrDocx(asDocTo(updated), rootPath, false, true));
        docValuedFieldsRepository.deleteAll(id);

        List<DocAgreement> docAgreementList = docAgreementRepository.getAll(updated.getId());
        DocAgreement daCurrent = docAgreementList.stream().filter(DocAgreement::isCurrentUser).findFirst().orElse(null);
        if (daCurrent != null) {
            daCurrent.setComment(comment);
            daCurrent.setDecisionType(DecisionType.ACCEPTED);
            daCurrent.setAgreedDateTime(LocalDateTime.now());
            daCurrent.setCurrentUser(false);
            docAgreementRepository.save(daCurrent);
            if (!isFinalAgreementStage) {
                DocAgreement daNext = docAgreementRepository.getByOrder(docTo.getId(), daCurrent.getOrdering() + 1);
                daNext.setCurrentUser(true);
                docAgreementRepository.save(daNext);
                User user = userRepository.findById(daNext.getUser().getId()).orElse(null);
                mailService.sendAgreementEmail(user.getEmail(), docTo.getId(), updated.getProjectRegNum());
            }
        }
        updated = checkNotFoundWithId(docRepository.save(updated), id);
        DocTo updatedTo = asDocTo(updated);
        updatedTo.setCanAgree(hasRights);
        if (updated.getDocStatus() == DocStatus.IN_WORK) {
            for (Resolution resolution : updated.getResolutions()) {
                mailService.sendDistributionEmail(resolution.getDepartment().getChiefUser().getEmail(),
                        updated.getId(), updated.getRegNum());
            }
        }
        return asDocTo(updated);
    }

    public static void moveFile(String oldPath, String newPath) {
        new File(newPath).delete();
        File oldFile = new File(oldPath);
        oldFile.renameTo(new File(newPath));
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Assert.notNull(id, "doc must not be null");
        checkNotFoundWithId(docRepository.delete(id)!= 0, id);
    }

    @Override
    public DocTo rejectDocAgreement(int id, String userName, String comment) throws NotFoundException {
        Doc updated = checkNotFoundWithId(docRepository.findById(id).orElse(null), id);
        updated.setDocStatus(DocStatus.AGREEMENT_REJECTED);
        docRepository.save(updated);
        List<DocAgreement> docAgreementList = docAgreementRepository.getAll(id);
        DocAgreement daCurrent = docAgreementList.stream().filter(DocAgreement::isCurrentUser).findFirst().orElse(null);
        daCurrent.setAgreedDateTime(LocalDateTime.now());
        daCurrent.setComment(comment);
        daCurrent.setDecisionType(DecisionType.REJECTED);
        docAgreementRepository.save(daCurrent);
        return asDocTo(checkNotFoundWithId(docRepository.save(updated), id));
    }

    @Override
    public List<User> saveExecutorUsersList(int id, List<User> executorUsers) {
        Doc doc = docRepository.findById(id).orElse(null);
        List<User> ex = Optional.ofNullable(executorUsers)
                .map(Collection::stream).orElseGet(Stream::empty)
                .map(u -> userRepository.findById(u.getId()).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
        List<Department> allResDeps = doc.getResolutions().stream().map(r -> r.getDepartment()).collect(Collectors.toList());
        List<Integer> allResDepIds = allResDeps
                .stream()
                .flatMap(r -> Optional.ofNullable(r.getChildDepartments()).map(Collection::stream).orElseGet(Stream::empty))
                .map(Department::getId).collect(Collectors.toList());
        allResDeps.stream().forEach(d -> allResDepIds.add(d.getId()));
        for (User user: ex) {
            if (user.getDepartment() == null || !allResDepIds.contains(user.getDepartment().getId())) {
                throw new IllegalResolutionUserException();
            }
        }
        for (Resolution resolution: doc.getResolutions()) {
            // получаем управления, на которое сделана резолюция
            Department resDep = resolution.getDepartment();
            // получаем id дочерних подразделений управления, на которое сделана резолюция
            List<Integer> resDepIds = Optional.ofNullable(resDep.getChildDepartments())
                    .map(Collection::stream)
                    .orElseGet(Stream::empty)
                    .map(d -> d.getId()).collect(Collectors.toList());
            resDepIds.add(resDep.getId());
            // фильтуем сохраняемых юзеров, чтобы они соответствовали текущему на данной итерации управлению
            List<User> newUsers = ex.stream().filter(u -> resDepIds.contains(u.getDepartment().getId())).collect(Collectors.toList());
            List<ResolutionsUsers> resolutionsUsers =
                    resolution.getResolutionsUsers().stream()
                            .filter(ru -> newUsers.contains(ru.getUser())).collect(Collectors.toList());
            List<User> curResUsers = resolutionsUsers
                    .stream().map(cru -> cru.getUser()).collect(Collectors.toList());
            List<User> usersToAdd = newUsers.stream().filter(ru -> !curResUsers.contains(ru)).collect(Collectors.toList());
            for (User user : usersToAdd) {
                resolutionsUsers.add(new ResolutionsUsers(null, LocalDateTime.now(), user, resolution));
            }
            resolution.setResolutionsUsers(resolutionsUsers);
        }
        executorUsers.stream().forEach(x -> {
            User u = userRepository.findById(x.getId()).orElse(null);
            mailService.sendExecutionEmail(u.getEmail(), doc.getId(), doc.getRegNum());
        });
        return docRepository.save(doc).getResolutions().stream().flatMap(r -> r.getResolutionsUsers().stream())
                .map(ru -> ru.getUser()).collect(Collectors.toList());
    }

    private void fillTags(FieldTo fieldTo, Map<String, String> simpleTags, Map<String, TaggedTable> taggedTables, Integer maxCellsCount) {
        String tag = fieldTo.getTag();
        if (TagUtil.getTableTag(tag) != null) {
            Map<String, String> cellsTags;
            String tableTag = TagUtil.getTableTag(tag);
            if (!taggedTables.containsKey(tableTag)) {
                cellsTags = new HashMap<>();
                taggedTables.put(tableTag, new TaggedTable(tableTag, new ArrayList<TableRow>() {{
                    add(new TableRow(cellsTags));}}));
            } else {
                List<TableRow> rows = taggedTables.get(tableTag).getRows();
                if (rows.get(rows.size()-1).getCellsTags().size() == maxCellsCount) {
                    cellsTags = new HashMap<>();
                    rows.add((new TableRow(cellsTags)));
                }
                else
                {
                    cellsTags = rows.get(rows.size()-1).getCellsTags();
                }
            }
            switch (fieldTo.getFieldType()) {
                case GROUP_FIELDS:
                    for (FieldTo childField : fieldTo.getChildFields()) {
                        fillTags(childField, simpleTags, taggedTables, fieldTo.getChildFields().size());
                    }
                    break;
                case CATALOG:
                    CatalogElem catalogElemChild = null;
                    if (fieldTo.getValueInt() != null) {
                        catalogElemChild = catalogElemRepository.findById(fieldTo.getValueInt()).orElse(null);
                        switch (catalogElemChild.getCatalog().getCatalogType()) {
                            case TEXT:
                                cellsTags.put(tag, catalogElemChild.getValueStr());
                                break;
                            case NUMBER:
                                cellsTags.put(tag, catalogElemChild.getValueInt());
                                break;
                        }
                    }
                    else {
                        cellsTags.put(tag, "");
                    }
                    break;
                case CATALOG_USERS:
                    User user = userRepository.findById(fieldTo.getValueInt()).orElse(null);
                    cellsTags.put(tag, user.getFirstname().substring(0,1) + "." + user.getPatronym().substring(0,1) + ". " + user.getLastname() +
                            " " + "8(495)620-20-00, доб. " + user.getPhone());
                    break;
                case CATALOG_ORGANIZATIONS:
                    Organization organization = organizationRepository.findById(fieldTo.getValueInt()).orElse(null);
                    cellsTags.put(tag, organization.getShortNameLf());
                    break;
                case CATALOG_REGNUMBERS:
                    String regNum = docRepository.findById(fieldTo.getValueInt()).orElse(null).getRegNum();
                    cellsTags.put(tag, regNum);
                    break;
                default:
                    if (!tag.equals("")) {
                        cellsTags.put(tag, fieldTo.getValueByFieldType());
                    }
                    break;
            }
        } else
        if (TagUtil.getSimpleTag(tag) != null) {
            switch (fieldTo.getFieldType()) {
                case GROUP_FIELDS:
                    for (FieldTo childField : fieldTo.getChildFields()) {
                        int childMaxCellsCount = (int) fieldTo.getChildFields().stream()
                                .filter(c -> !c.getTag().equals("")).count();
                        fillTags(childField, simpleTags, taggedTables, childMaxCellsCount);
                    }
                    break;
                case CATALOG:
                    CatalogElem catalogElemChild =
                            catalogElemRepository.findById(fieldTo.getValueInt()).orElse(null);
                    switch (catalogElemChild.getCatalog().getCatalogType()) {
                        case TEXT:
                            simpleTags.put(tag, catalogElemChild.getValueStr());
                            break;
                        case NUMBER:
                            simpleTags.put(tag, catalogElemChild.getValueInt());
                            break;
                    }
                case CATALOG_USERS:
                    User user = userRepository.findById(fieldTo.getValueInt()).orElse(null);
                    simpleTags.put(tag, user.getFirstname().substring(0,1) + "." + user.getPatronym().substring(0,1) + ". " + user.getLastname() +
                            " " + "8(495)620-20-00, доб. " + user.getPhone());
                    break;
                case CATALOG_ORGANIZATIONS:
                    Organization organization = organizationRepository.findById(fieldTo.getValueInt()).orElse(null);
                    simpleTags.put(tag, organization.getShortNameLf());
                    break;
                case CATALOG_REGNUMBERS:
                    String regNum = docRepository.findById(fieldTo.getValueInt()).orElse(null).getRegNum();
                    simpleTags.put(tag, regNum);
                    break;
                default:
                    if (!tag.equals("")) {
                        simpleTags.put(tag, fieldTo.getValueByFieldType());
                    }
                    break;
            }
        }
    }

    @Override
    public FileTo uploadFile(MultipartFile inputFile, String rootPath) throws FileUploadException {
        String savePath = rootPath + uploadsTempDir + UUID.randomUUID().toString() + "_" + inputFile.getOriginalFilename();
        try {
            inputFile.transferTo(new File(savePath));
        }
         catch (IOException e) {
            throw new FileUploadException();
        }
        return new FileTo(savePath);
    }

    @Override
    public FileTo createDOCX(DocTo docTo, String rootPath) {
        Integer finalUserId = docTo.getFinalUserId();
        DocTo docToFull = asDocTo(createNewDocFromTo(docTo));
        docToFull.setFinalUserId(finalUserId);
        return new FileTo(createPDFOrDocx(docToFull, rootPath,  true, false));
    }

    @Override
    public FileTo createPDF(DocTo docTo, String rootPath) {
        Integer finalUserId = docTo.getFinalUserId();
        DocTo docToFull = asDocTo(createNewDocFromTo(docTo));
        docToFull.setFinalUserId(finalUserId);
        return new FileTo(createPDFOrDocx(docToFull, rootPath,  true, true));
    }

    private String createPDFOrDocx(DocTo docTo, String rootPath, Boolean saveToTempDir, Boolean isPDF) {
        DocType docType = docTypeRepository.findById(docTo.getDocTypeId()).orElse(null);
        String templatePath = rootPath + docTemplatesDir + docType.getTemplateFileName();
        String tmpTemplatePath = rootPath + docTemplatesDir + docType.getTmpTemplateFileName();

        String genTemplatePath = isPDF ? templatePath : tmpTemplatePath;

        String pdfTempPath = rootPath + pdfTempDir + UUID.randomUUID().toString() + ".pdf";
        String pdfPath = rootPath + pdfDir + docTo.getId() + ".pdf";

        String pdfSavePath = saveToTempDir ? pdfTempPath : pdfPath;
        String docxSavePath = rootPath + docxTempDir + UUID.randomUUID().toString() + ".docx";

        String savePath = isPDF ? pdfSavePath : docxSavePath;

        Map<String, String> simpleTags = new HashMap<>();
        simpleTags.put("RegNum", Optional.ofNullable(docTo.getRegNum() != null ? docTo.getRegNum() : docTo.getProjectRegNum()).orElse(""));
        if (docTo.getFinalUserId() != null) {
            User finalUser = userRepository.findById(docTo.getFinalUserId()).orElse(null);
            if (finalUser != null) {
                simpleTags.put("SignerPosition", finalUser.getShortPosition());
                simpleTags.put("SignerFullPosition", finalUser.getFullPosition());
                simpleTags.put("Signer", finalUser.getFirstname().substring(0, 1) + "." + finalUser.getPatronym().substring(0, 1)
                        + "." + finalUser.getLastname());
            }
        }


        if (docTo.getExecutorDepartmentsIds() != null && !docTo.getExecutorDepartmentsIds().isEmpty()) {
            for (int i = 0; i < docTo.getExecutorDepartmentsIds().size(); i++) {
                Department exDep = departmentRepository.findById(docTo.getExecutorDepartmentsIds().get(i)).orElse(null);
                List<FieldTo> exDepsFields = new ArrayList<>();
                exDepsFields.add(new FieldTo(null, FieldType.TEXT, exDep.getChiefUser().getDativePosition(), "[To]Department"));
                exDepsFields.add(new FieldTo(null, FieldType.TEXT, exDep.getChiefUser().getDativeFullname(), "[To]Chief"));
                docTo.getChildFields().add(new DocFieldsTo(null, new FieldTo(exDepsFields, FieldType.GROUP_FIELDS, ""), null));
            }
        } else {
            List<FieldTo> exDepsFields = new ArrayList<>();
            exDepsFields.add(new FieldTo(null, FieldType.TEXT, "", "[To]Department"));
            exDepsFields.add(new FieldTo(null, FieldType.TEXT, "", "[To]Chief"));
            docTo.getChildFields().add(new DocFieldsTo(null, new FieldTo(exDepsFields, FieldType.GROUP_FIELDS, ""), null));
        }

        Map<String, TaggedTable> taggedTables = new HashMap<>();
        for (DocFieldsTo docFieldsTo : docTo.getChildFields()) {
            fillTags(docFieldsTo.getField(), simpleTags, taggedTables, docTo.getChildFields().size());
        }

        try {
            ByteArrayOutputStream byteArrayOutputStream =
                    Templater.fillTagsByDictionary(genTemplatePath, simpleTags, taggedTables, isPDF);
            byteArrayOutputStream.writeTo(new FileOutputStream(savePath));
        } catch (Exception e) {
            throw new GeneratePdfException();
        }

        return savePath.replace(rootPath,"");
    }

    private DocTo asDocTo(Doc doc) {
        Integer docTypeId = doc.getDocType().getId();
        List<String> curUserRoles = AuthorizedUser.getRoles();

        List<DocValuedFields> docValuedFields = doc.getDocValuedFields();
        List<DocFieldsTo> docFieldsTos = new ArrayList<>();
        List<FieldsRoles> fieldsRoles = fieldsRolesRepository.getAll(docTypeId);
        Map<Integer, FieldsRoles> fMap = fieldsRoles.stream()
                .collect(Collectors.toMap(FieldsRoles::getFieldId, f -> f));

        boolean deny = false;
        boolean canAgree = false;
        if (doc.getId() != null) {
            canAgree = docAgreementRepository.isTimeForAgreeForUser(doc.getId(), AuthorizedUser.getUserName())
                    .orElse(false);
        }
        if (doc.getDocStatus() == DocStatus.IN_WORK || (!canAgree && !AuthorizedUser.hasRole("ADMIN"))) {
            deny = true;
        }

        for (DocValuedFields d:docValuedFields) {
            docFieldsTos.add(new DocFieldsTo(d.getId(),
                    FieldUtil.asTo(d.getValuedField(), curUserRoles, (HashMap<Integer, FieldsRoles>) fMap, deny, false), d.getPosition()));
        }

        boolean isFinalAgreementStage = false;
        if (doc.getId() != null) {
            isFinalAgreementStage = isFinalAgreementStage(doc.getId());
        }

        List<Integer> executorDepartmentsIds = null;
        List<Integer> executorUsersIds = null;
        if (doc.getResolutions() != null) {
            executorDepartmentsIds = doc.getResolutions().stream()
                    .sorted(Comparator.comparing(Resolution::isPrimaryResolution))
                    .map(Resolution::getDepartment)
                    .map(Department::getId)
                    .collect(Collectors.toList());
            executorUsersIds = doc.getResolutions()
                    .stream().flatMap(r -> Optional.ofNullable(r.getResolutionsUsers()).map(Collection::stream).orElseGet(Stream::empty))
                        .map(r -> r.getUser().getId()).collect(Collectors.toList());
        }

        User initialUser = doc.getInitialUser();
        UserTo initialUserTo = null;
        if (initialUser != null) {
            initialUserTo = new UserTo(initialUser.getId(), initialUser.getLastname() + " "
                    + initialUser.getFirstname() + " " + initialUser.getPatronym(), initialUser.getPhone(),
                    initialUser.getPosition());
        }


        Integer finalUserId = null;
        if (doc.getId() != null) {
            User finalUser = docAgreementRepository.getFinalUser(doc.getId());
            if (finalUser != null) {
                finalUserId = finalUser.getId();
            }
        }

        return new DocTo(doc.getId(), doc.getRegNum(), doc.getRegDateTime(), doc.getProjectRegNum(),
                doc.getProjectRegDateTime(), doc.getInsertDateTime(), doc.getDocType().getId(),
                doc.getDocStatus(), isFinalAgreementStage, false, false, false, doc.getUrlPDF(),
                initialUserTo, finalUserId, doc.getDocType().isFinalDoc(), null, executorDepartmentsIds,
                executorUsersIds, docFieldsTos, doc.getParentDoc() != null ? doc.getParentDoc().getId(): null);
    }

    public ValuedField createNewValueFieldFromTo(FieldTo newField) {
        List<ValuedField> childFields = new ArrayList<>();
        for (FieldTo childFieldTo : newField.getChildFields()) {
            childFields.add(createNewValueFieldFromTo(childFieldTo));
        }

        Field field = fieldRepository.findById(newField.getFieldId()).orElse(null);
        CatalogElem catalogElem = null;
        if (field.getFieldType() == FieldType.CATALOG) {
            if (newField.getValueInt() != null) {
                catalogElem = catalogElemRepository.findById(newField.getValueInt()).orElse(null);
                newField.setValueInt(null);
            }
        }

        return new ValuedField(null, childFields,  field, catalogElem, newField.getValueInt(), newField.getValueStr(),
                newField.getValueDate(), newField.getValueDate(), newField.getValueDate());
    }

    private Doc createNewDocFromTo(DocTo docTo) {
        DocType docType = docTypeRepository.findById(docTo.getDocTypeId()).orElse(null);

        Doc doc = new Doc(null, docTo.getRegNum(), docTo.getRegDateTime(), docTo.getProjectRegNum(),
                docTo.getProjectRegDateTime(), docTo.getInsertDateTime(), docType, null,
                null, null, docTo.getUrlPDF(), null);

        List<Department> executorDepartments = Optional.ofNullable(docTo.getExecutorDepartmentsIds())
                .map(Collection::stream).orElseGet(Stream::empty)
                .map(d -> departmentRepository.findById(d).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());

        List<Resolution> resolutions = new ArrayList<>();
        for (int i = 0; i < executorDepartments.size(); i++) {
            resolutions
                    .add(new Resolution(null, i+1, doc, i==0, executorDepartments.get(i)));
        }

        doc.setResolutions(resolutions);
        doc.setDocValuedFields(createNewValuedFieldsByDoc(doc, docTo.getChildFields()));
        return doc;
    }

    private Doc docFromTo(DocTo docTo) {
        DocType docType = docTypeRepository.findById(docTo.getDocTypeId()).orElse(null);
        Doc exDoc = checkNotFoundWithId(docRepository.findById(docTo.getId()).orElse(null), docTo.getId());
        Doc doc = new Doc(exDoc.getId(), exDoc.getRegNum(), exDoc.getRegDateTime(), exDoc.getProjectRegNum(),
                exDoc.getProjectRegDateTime(), exDoc.getInsertDateTime(), docType, exDoc.getResolutions(),
                null, exDoc.getInitialUser(), exDoc.getUrlPDF(), exDoc.getParentDoc());

        doc.setDocValuedFields(createNewValuedFieldsByDoc(doc, docTo.getChildFields()));

        return doc;
    }

    private List<DocValuedFields> createNewValuedFieldsByDoc(Doc doc, List<DocFieldsTo> docFieldsTos) {
        List<DocValuedFields> docValuedFields = new ArrayList<>();
        for (DocFieldsTo d:docFieldsTos) {
            docValuedFields.add(new DocValuedFields(null, doc,
                    createNewValueFieldFromTo(d.getField()), d.getPosition()));
        }
        return docValuedFields;
    }
}
