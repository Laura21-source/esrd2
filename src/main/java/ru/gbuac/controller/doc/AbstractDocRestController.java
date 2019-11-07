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
import java.util.HashMap;
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
                        docService.getAllAgreement().size() : docService.getAllAgreementByUsername(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "agreementMoreDeadlineByUserName", docService.getAllAgreementMoreDeadlineByUserName(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "agreementLessDeadlineByUserName", docService.getAllAgreementLessDeadlineByUserName(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },

                { "inwork", docService.getAllInWorkByUserName(AuthorizedUser.getUserName()).size() },
                { "inworkMoreDeadlineByUserName", docService.getAllInWorkMoreDeadlineByUserName(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "inworkLessDeadlineByUserName", docService.getAllInWorkLessDeadlineByUserName(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },

                { "distribution", docService.getAllDistribution(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "distributionMoreDeadlineByChiefUserName", docService.getAllDistributionMoreDeadlineByChiefUserName(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "distributionLessDeadlineByChiefUserName", docService.getAllDistributionLessDeadlineByChiefUserName(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },

                { "atThisMounthOnControl", docService.getAllAtThisMounthOnControl(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "atThisMounthOnControlCompletedInTime", docService.getAllAtThisMounthOnControlCompletedInTime(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "atThisMounthOnControlCompletedAfterTime", docService.getAllAtThisMounthOnControlCompletedAfterTime(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "atThisMounthOnControlNotCompleted", docService.getAllAtThisMounthOnControlNotCompleted(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },

                { "agreementMoreDeadlineByDepartment", docService.getAllAgreementMoreDeadlineByDepartment(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "agreementLessDeadlineByDepartment", docService.getAllAgreementLessDeadlineByDepartment(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "inworkMoreDeadlineByDepartment", docService.getAllInWorkMoreDeadlineByDepartment(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "inworkLessDeadlineByDepartment", docService.getAllInWorkLessDeadlineByDepartment(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "distributionMoreDeadlineByDepartment", docService.getAllDistributionMoreDeadlineByDepartment(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
                { "distributionLessDeadlineByDepartment", docService.getAllDistributionLessDeadlineByDepartment(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName()).size() },
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

        return map;
    }

    public List<Doc> getAllAgreementMoreDeadlineByUserName() {
        LOG.info("getAllAgreementMoreDeadlineByUserName");
        if(AuthorizedUser.getDelegatedUser() != null) {
            docService.getAllAgreementMoreDeadlineByUserName(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllAgreementMoreDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreementLessDeadlineByUserName() {
        LOG.info("getAllAgreementLessDeadlineByUserName");
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllAgreementLessDeadlineByUserName(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllAgreementLessDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreementMoreDeadlineByDepartment() {
        LOG.info("getAllAgreementMoreDeadlineByDepartment");
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllAgreementMoreDeadlineByDepartment(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllAgreementMoreDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreementLessDeadlineByDepartment() {
        LOG.info("getAllAgreementLessDeadlineByDepartment");
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllAgreementLessDeadlineByDepartment(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllAgreementLessDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public Doc get(int id) {
        LOG.info("get " + id);
        return docService.get(id);
    }

    public DocTo getFull(int id) {
        LOG.info("get " + id);
        if (AuthorizedUser.getDelegatedUser() != null) {
            return docService.getFullByUserName(id, AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getFullByUserName(id, AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreement() {
        LOG.info("getAllAgreement");
        return AuthorizedUser.hasRole("ADMIN") ?
                docService.getAllAgreement() : docService.getAllAgreementByUsername(
        AuthorizedUser.getDelegatedUser() == null ?
            AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreed() {
        LOG.info("getAllAgreed");
        return AuthorizedUser.hasRole("ADMIN") ?
                docService.getAllRegistered() : docService.getAllAgreedByUsername(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName());
    }

    public List<Doc> getAllRegistered() {
        LOG.info("getAllRegistered");
        return AuthorizedUser.hasRole("ADMIN") ?
                docService.getAllRegistered() : docService.getAllRegisteredByUsername(AuthorizedUser.getDelegatedUser() != null ? AuthorizedUser.getDelegatedUser().getName() : AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllInWorkByUserName() {
        LOG.info("getAllInWorkByUserName");
        if (AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllInWorkByUserName(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllInWorkByUserName(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllInWorkMoreDeadlineByUserName() {
        LOG.info("getAllInWorkMoreDeadlineByUserName");
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllInWorkMoreDeadlineByUserName(AuthorizedUser.getDelegatedUser().getName());
        } return docService.getAllInWorkMoreDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllInWorkLessDeadlineByUserName() {
        LOG.info("getAllInWorkLessDeadlineByUserName");
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllInWorkLessDeadlineByUserName(AuthorizedUser.getDelegatedUser().getName());
        } return docService.getAllInWorkLessDeadlineByUserName(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllInWorkMoreDeadlineByDepartment() {
        LOG.info("getAllInWorkMoreDeadlineByDepartment");
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllInWorkMoreDeadlineByDepartment(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllInWorkMoreDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllInWorkLessDeadlineByDepartment() {
        LOG.info("getAllInWorkLessDeadlineByDepartment");
        if(AuthorizedUser.getDelegatedUser() != null) {
           return docService.getAllInWorkLessDeadlineByDepartment(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllInWorkLessDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistribution() {
        LOG.info("getAllDistribution");
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllDistribution(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllDistribution(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistributionMoreDeadlineByChiefUserName() {
        LOG.info("getAllDistributionMoreDeadlineByChiefUserName");
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllDistributionMoreDeadlineByChiefUserName(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllDistributionMoreDeadlineByChiefUserName(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistributionLessDeadlineByChiefUserName() {
        LOG.info("getAllDistributionLessDeadlineByChiefUserName");
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllDistributionLessDeadlineByChiefUserName(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllDistributionLessDeadlineByChiefUserName(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistributionMoreDeadlineByDepartment() {
        LOG.info("getAllDistributionMoreDeadlineByDepartment");
        if (AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllDistributionMoreDeadlineByDepartment(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllDistributionMoreDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistributionLessDeadlineByDepartment() {
        LOG.info("getAllDistributionLessDeadlineByDepartment");
        if (AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllDistributionLessDeadlineByDepartment(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllDistributionLessDeadlineByDepartment(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllDistributed() {
        LOG.info("getAllDistributed");
        if (AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllDistributed(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllDistributed(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllAtThisMonthOnControl() {
        LOG.info("getAllAtThisMonthOnControl");
        if (AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllAtThisMounthOnControl(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllAtThisMounthOnControl(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllAtThisMonthOnControlCompletedInTime() {
        LOG.info("getAllAtThisMonthOnControlCompletedInTime");
        if (AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllAtThisMounthOnControlCompletedInTime(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllAtThisMounthOnControlCompletedInTime(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllAtThisMonthOnControlCompletedAfterTime() {
        LOG.info("getAllAtThisMonthOnControlCompletedAfterTime");
        if (AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllAtThisMounthOnControlCompletedAfterTime(AuthorizedUser.getDelegatedUser().getName());
        }
        return docService.getAllAtThisMounthOnControlCompletedAfterTime(AuthorizedUser.getUserName());
    }

    public List<DocItemTo> getAllAtThisMonthOnControlNotCompleted() {
        LOG.info("getAllAtThisMonthOnControlNotCompleted");
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.getAllAtThisMounthOnControlNotCompleted(AuthorizedUser.getDelegatedUser().getName());
        } return docService.getAllAtThisMounthOnControlNotCompleted(AuthorizedUser.getUserName());
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
        if(AuthorizedUser.getDelegatedUser() != null) {
            docService.save(docTo, AuthorizedUser.getDelegatedUser().getName(), rootPath);
        }
        return docService.save(docTo, AuthorizedUser.getUserName(), rootPath);
    }

    public DocTo update(DocTo docTo, int id, String rootPath) {
        LOG.info("update " + docTo);
        assureIdConsistent(docTo, id);
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.update(docTo, id, AuthorizedUser.getDelegatedUser().getName(), rootPath);
        }
        return docService.update(docTo, id, AuthorizedUser.getUserName(), rootPath);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        docService.delete(id);
    }

    public DocTo rejectDocAgreement(int id,  String comment) {
        LOG.info("returnDocAgreement " + id);
        if(AuthorizedUser.getDelegatedUser() != null) {
            return docService.rejectDocAgreement(id, AuthorizedUser.getDelegatedUser().getName(), comment);
        }
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
}
