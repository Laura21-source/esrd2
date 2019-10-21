package ru.gbuac.controller.doc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import ru.gbuac.AuthorizedUser;
import ru.gbuac.model.Doc;
import ru.gbuac.model.User;
import ru.gbuac.service.DocService;
import ru.gbuac.to.*;

import javax.validation.Valid;
import java.util.List;
import static ru.gbuac.util.ValidationUtil.assureIdConsistent;
import static ru.gbuac.util.ValidationUtil.checkNew;

public abstract class AbstractDocRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    protected DocService docService;

    public List<Doc> getAllAgreementMoreDeadlineByUserName() {
        LOG.info("getAllAgreementMoreDeadlineByUserName");
        return docService.getAllAgreementMoreDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public Integer getCountAgreementMoreDeadlineByUserName() {
        LOG.info("getCountAgreementMoreDeadlineByUserName");
        return docService.getAllAgreementMoreDeadlineByUserName(AuthorizedUser.getUserName()).size();
    }

    public List<Doc> getAllAgreementLessDeadlineByUserName() {
        LOG.info("getAllAgreementLessDeadlineByUserName");
        return docService.getAllAgreementLessDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public Integer getCountAgreementLessDeadlineByUserName() {
        LOG.info("getCountAgreementLessDeadlineByUserName");
        return docService.getAllAgreementLessDeadlineByUserName(AuthorizedUser.getUserName()).size();
    }

    public List<Doc> getAllAgreementMoreDeadlineByDepartment() {
        LOG.info("getAllAgreementMoreDeadlineByDepartment");
        return docService.getAllAgreementMoreDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public Integer getCountAgreementMoreDeadlineByDepartment() {
        LOG.info("getCountAgreementMoreDeadlineByDepartment");
        return docService.getAllAgreementMoreDeadlineByDepartment(AuthorizedUser.getUserName()).size();
    }

    public List<Doc> getAllAgreementLessDeadlineByDepartment() {
        LOG.info("getAllAgreementLessDeadlineByDepartment");
        return docService.getAllAgreementLessDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public Integer getCountAgreementLessDeadlineByDepartment() {
        LOG.info("getCountAgreementLessDeadlineByDepartment");
        return docService.getAllAgreementLessDeadlineByDepartment(AuthorizedUser.getUserName()).size();
    }


    public Doc get(int id) {
        LOG.info("get " + id);
        return docService.get(id);
    }

    public DocTo getFull(int id) {
        LOG.info("get " + id);
        return docService.getFullByUserName(id, AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreement() {
        LOG.info("getAllAgreement");
        return AuthorizedUser.hasRole("ADMIN") ?
                docService.getAllAgreement() : docService.getAllAgreementByUsername(AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreed() {
        LOG.info("getAllAgreed");
        return AuthorizedUser.hasRole("ADMIN") ?
                docService.getAllRegistered() : docService.getAllAgreedByUsername(AuthorizedUser.getUserName());
    }

    public List<Doc> getAllRegistered() {
        LOG.info("getAllRegistered");
        return AuthorizedUser.hasRole("ADMIN") ?
                docService.getAllRegistered() : docService.getAllRegisteredByUsername(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllInWorkByUserName() {
        LOG.info("getAllInWorkByUserName");
         return docService.getAllInWorkByUserName(AuthorizedUser.getUserName());
    }

    public Integer getCountInWorkByUserName() {
        LOG.info("getCountInWorkByUserName");
        return docService.getAllInWorkByUserName(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllInWorkMoreDeadlineByUserName() {
        LOG.info("getAllInWorkMoreDeadlineByUserName");
        return docService.getAllInWorkMoreDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public Integer getCountInWorkMoreDeadlineByUserName() {
        LOG.info("getCountInWorkMoreDeadlineByUserName");
        return docService.getAllInWorkMoreDeadlineByUserName(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllInWorkLessDeadlineByUserName() {
        LOG.info("getAllInWorkLessDeadlineByUserName");
        return docService.getAllInWorkLessDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public Integer geCountInWorkLessDeadlineByUserName() {
        LOG.info("getCountInWorkLessDeadlineByUserName");
        return docService.getAllInWorkLessDeadlineByUserName(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllInWorkMoreDeadlineByDepartment() {
        LOG.info("getAllInWorkMoreDeadlineByDepartment");
        return docService.getAllInWorkMoreDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public Integer getCountInWorkMoreDeadlineByDepartment() {
        LOG.info("getAllInWorkMoreDeadlineByDepartment");
        return docService.getAllInWorkMoreDeadlineByDepartment(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllInWorkLessDeadlineByDepartment() {
        LOG.info("getAllInWorkLessDeadlineByDepartment");
        return docService.getAllInWorkLessDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public Integer getCountInWorkLessDeadlineByDepartment() {
        LOG.info("getCountInWorkLessDeadlineByDepartment");
        return docService.getAllInWorkLessDeadlineByDepartment(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllDistribution() {
        LOG.info("getAllDistribution");
        return docService.getAllDistribution(AuthorizedUser.getUserName());
    }

    public Integer getCountDistribution() {
        LOG.info("getCountDistribution");
        return docService.getAllDistribution(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllDistributionMoreDeadlineByChiefUserName() {
        LOG.info("getAllDistributionMoreDeadlineByChiefUserName");
        return docService.getAllDistributionMoreDeadlineByChiefUserName(AuthorizedUser.getUserName());
    }

    public Integer getCountDistributionMoreDeadlineByChiefUserName() {
        LOG.info("getCountDistributionMoreDeadlineByChiefUserName");
        return docService.getAllDistributionMoreDeadlineByChiefUserName(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllDistributionLessDeadlineByChiefUserName() {
        LOG.info("getAllDistributionLessDeadlineByChiefUserName");
        return docService.getAllDistributionLessDeadlineByChiefUserName(AuthorizedUser.getUserName());
    }

    public Integer getCountDistributionLessDeadlineByChiefUserName() {
        LOG.info("getCountDistributionLessDeadlineByChiefUserName");
        return docService.getAllDistributionLessDeadlineByChiefUserName(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllDistributionMoreDeadlineByDepartment() {
        LOG.info("getAllDistributionMoreDeadlineByDepartment");
        return docService.getAllDistributionMoreDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public Integer getCountDistributionMoreDeadlineByDepartment() {
        LOG.info("getCountDistributionMoreDeadlineByDepartment");
        return docService.getAllDistributionMoreDeadlineByDepartment(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllDistributionLessDeadlineByDepartment() {
        LOG.info("getAllDistributionLessDeadlineByDepartment");
        return docService.getAllDistributionLessDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public Integer getCountDistributionLessDeadlineByDepartment() {
        LOG.info("getCountDistributionLessDeadlineByDepartment");
        return docService.getAllDistributionLessDeadlineByDepartment(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllDistributed() {
        LOG.info("getAllDistributed");
        return docService.getAllDistributed(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllAtThisMounthOnControl() {
        LOG.info("getAllAtThisMounthOnControl");
        return docService.getAllAtThisMounthOnControl(AuthorizedUser.getUserName());
    }

    public Integer getCountAtThisMounthOnControl() {
        LOG.info("getCountAtThisMounthOnControl");
        return docService.getAllAtThisMounthOnControl(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllAtThisMounthOnControlCompletedInTime() {
        LOG.info("getAllAtThisMounthOnControlCompletedInTime");
        return docService.getAllAtThisMounthOnControlCompletedInTime(AuthorizedUser.getUserName());
    }

    public Integer getCountAtThisMounthOnControlCompletedInTime() {
        LOG.info("getCountAtThisMounthOnControlCompletedInTime");
        return docService.getAllAtThisMounthOnControlCompletedInTime(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllAtThisMounthOnControlCompletedAfterTime() {
        LOG.info("getAllAtThisMounthOnControlCompletedAfterTime");
        return docService.getAllAtThisMounthOnControlCompletedAfterTime(AuthorizedUser.getUserName());
    }

    public Integer getCountAtThisMounthOnControlCompletedAfterTime() {
        LOG.info("getCountAtThisMounthOnControlCompletedAfterTime");
        return docService.getAllAtThisMounthOnControlCompletedAfterTime(AuthorizedUser.getUserName()).size();
    }

    public List<DocItemTo> getAllAtThisMounthOnControlNotCompleted() {
        LOG.info("getAllAtThisMounthOnControlNotCompleted");
        return docService.getAllAtThisMounthOnControlNotCompleted(AuthorizedUser.getUserName());
    }

    public Integer getCountAtThisMounthOnControlNotCompleted() {
        LOG.info("getCountAtThisMounthOnControlNotCompleted");
        return docService.getAllAtThisMounthOnControlNotCompleted(AuthorizedUser.getUserName()).size();
    }


    public List<DocNumberTo> getRegNumbers() {
        LOG.info("getRegNumbers");
        return docService.getRegNumbers();
    }

    public List<DocItemTo> getAll() {
        LOG.info("getAll");
        return docService.getAll();
    }

    public DocTo create(DocTo docTo, String rootPath) {
        LOG.info("createFinal " + docTo);
        checkNew(docTo);
        return docService.save(docTo, AuthorizedUser.getUserName(), rootPath);
    }

    public DocTo update(DocTo docTo, int id, String rootPath) {
        LOG.info("update " + docTo);
        assureIdConsistent(docTo, id);
        return docService.update(docTo, id, AuthorizedUser.getUserName(), rootPath);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        docService.delete(id);
    }

    public DocTo rejectDocAgreement(int id,  String comment) {
        LOG.info("returnDocAgreement " + id);
        return docService.rejectDocAgreement(id, AuthorizedUser.getUserName(), comment);
    }

    public List<User> saveExecutorUsersList(int id, List<User> executorUsers) {
        return docService.saveExecutorUsersList(id, executorUsers);
    }

    public FileTo uploadFile(MultipartFile inputFile, String rootPath) {
        return docService.uploadFile(inputFile, rootPath);
    }

    public FileTo createDocx(DocTo docTo, String rootPath) {
        return docService.createDOCX(docTo, rootPath);
    }

    public FileTo createPDF(DocTo docTo, String rootPath) {
        return docService.createPDF(docTo, rootPath);
    }
}
