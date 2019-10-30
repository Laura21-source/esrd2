package ru.gbuac.controller.doc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import ru.gbuac.AuthorizedUser;
import ru.gbuac.model.Doc;
import ru.gbuac.service.DocService;
import ru.gbuac.to.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.gbuac.util.ValidationUtil.assureIdConsistent;
import static ru.gbuac.util.ValidationUtil.checkNew;

public abstract class AbstractDocRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    protected DocService docService;

    public Map<String, Integer> getCounters() {
        LOG.info("getCounters");
        String userName = AuthorizedUser.getUserName();
        Map<String, Integer> map = Stream.of(new Object[][] {
                { "agreement", AuthorizedUser.hasRole("ADMIN") ?
                        docService.getAllAgreement().size() : docService.getAllAgreementByUsername(userName).size() },
                { "agreementMoreDeadlineByUserName", docService.getAllAgreementMoreDeadlineByUserName(userName).size() },
                { "agreementLessDeadlineByUserName", docService.getAllAgreementLessDeadlineByUserName(userName).size() },

                { "inwork", docService.getAllInWorkByUserName(AuthorizedUser.getUserName()).size() },
                { "inworkMoreDeadlineByUserName", docService.getAllInWorkMoreDeadlineByUserName(userName).size() },
                { "inworkLessDeadlineByUserName", docService.getAllInWorkLessDeadlineByUserName(userName).size() },

                { "distribution", docService.getAllDistribution(userName).size() },
                { "distributionMoreDeadlineByChiefUserName", docService.getAllDistributionMoreDeadlineByChiefUserName(userName).size() },
                { "distributionLessDeadlineByChiefUserName", docService.getAllDistributionLessDeadlineByChiefUserName(userName).size() },

                { "atThisMounthOnControl", docService.getAllAtThisMonthOnControl(userName).size() },
                { "atThisMounthOnControlCompletedInTime", docService.getAllAtThisMonthOnControlCompletedInTime(userName).size() },
                { "atThisMounthOnControlCompletedAfterTime", docService.getAllAtThisMonthOnControlCompletedAfterTime(userName).size() },
                { "atThisMounthOnControlNotCompleted", docService.getAllAtThisMonthOnControlNotCompleted(userName).size() },

                { "agreementMoreDeadlineByDepartment", docService.getAllAgreementMoreDeadlineByDepartment(userName).size() },
                { "agreementLessDeadlineByDepartment", docService.getAllAgreementLessDeadlineByDepartment(userName).size() },
                { "inworkMoreDeadlineByDepartment", docService.getAllInWorkMoreDeadlineByDepartment(userName).size() },
                { "inworkLessDeadlineByDepartment", docService.getAllInWorkLessDeadlineByDepartment(userName).size() },
                { "distributionMoreDeadlineByDepartment", docService.getAllDistributionMoreDeadlineByDepartment(userName).size() },
                { "distributionLessDeadlineByDepartment", docService.getAllDistributionLessDeadlineByDepartment(userName).size() },
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

        return map;
    }

    public List<Doc> getAllAgreementMoreDeadlineByUserName() {
        LOG.info("getAllAgreementMoreDeadlineByUserName");
        return docService.getAllAgreementMoreDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreementLessDeadlineByUserName() {
        LOG.info("getAllAgreementLessDeadlineByUserName");
        return docService.getAllAgreementLessDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreementMoreDeadlineByDepartment() {
        LOG.info("getAllAgreementMoreDeadlineByDepartment");
        return docService.getAllAgreementMoreDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreementLessDeadlineByDepartment() {
        LOG.info("getAllAgreementLessDeadlineByDepartment");
        return docService.getAllAgreementLessDeadlineByDepartment(AuthorizedUser.getUserName());
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

    public List<DocItemTo> getAllInWorkMoreDeadlineByUserName() {
        LOG.info("getAllInWorkMoreDeadlineByUserName");
        return docService.getAllInWorkMoreDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllInWorkLessDeadlineByUserName() {
        LOG.info("getAllInWorkLessDeadlineByUserName");
        return docService.getAllInWorkLessDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllInWorkMoreDeadlineByDepartment() {
        LOG.info("getAllInWorkMoreDeadlineByDepartment");
        return docService.getAllInWorkMoreDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllInWorkLessDeadlineByDepartment() {
        LOG.info("getAllInWorkLessDeadlineByDepartment");
        return docService.getAllInWorkLessDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistribution() {
        LOG.info("getAllDistribution");
        return docService.getAllDistribution(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistributionMoreDeadlineByChiefUserName() {
        LOG.info("getAllDistributionMoreDeadlineByChiefUserName");
        return docService.getAllDistributionMoreDeadlineByChiefUserName(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistributionLessDeadlineByChiefUserName() {
        LOG.info("getAllDistributionLessDeadlineByChiefUserName");
        return docService.getAllDistributionLessDeadlineByChiefUserName(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistributionMoreDeadlineByDepartment() {
        LOG.info("getAllDistributionMoreDeadlineByDepartment");
        return docService.getAllDistributionMoreDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistributionLessDeadlineByDepartment() {
        LOG.info("getAllDistributionLessDeadlineByDepartment");
        return docService.getAllDistributionLessDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistributed() {
        LOG.info("getAllDistributed");
        return docService.getAllDistributed(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllAtThisMounthOnControl() {
        LOG.info("getAllAtThisMounthOnControl");
        return docService.getAllAtThisMonthOnControl(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllAtThisMounthOnControlCompletedInTime() {
        LOG.info("getAllAtThisMounthOnControlCompletedInTime");
        return docService.getAllAtThisMonthOnControlCompletedInTime(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllAtThisMounthOnControlCompletedAfterTime() {
        LOG.info("getAllAtThisMounthOnControlCompletedAfterTime");
        return docService.getAllAtThisMonthOnControlCompletedAfterTime(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllAtThisMounthOnControlNotCompleted() {
        LOG.info("getAllAtThisMounthOnControlNotCompleted");
        return docService.getAllAtThisMonthOnControlNotCompleted(AuthorizedUser.getUserName());
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

    public FileTo uploadFile(MultipartFile inputFile, String rootPath) {
        return docService.uploadFile(inputFile, rootPath);
    }

    public FileTo createDocx(DocTo docTo, String rootPath) {
        return docService.createDOCX(docTo, rootPath);
    }

    public FileTo createPDF(DocTo docTo, String rootPath) {
        return docService.createPDF(docTo, rootPath);
    }

//    public List<Doc> getAllChildDocs(int parentDocId) {
//        LOG.info("getAllChildDocs" + parentDocId);
//        return docService.getAllChildDocs(parentDocId);
//    }
}
