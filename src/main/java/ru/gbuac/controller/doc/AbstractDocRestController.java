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
import ru.gbuac.to.DocNumberTo;
import ru.gbuac.to.DocTo;
import ru.gbuac.to.FileTo;
import ru.gbuac.to.UserTo;

import javax.validation.Valid;
import java.util.List;
import static ru.gbuac.util.ValidationUtil.assureIdConsistent;
import static ru.gbuac.util.ValidationUtil.checkNew;

public abstract class AbstractDocRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    protected DocService docService;

    public Doc get(int id) {
        LOG.info("get " + id);
        return docService.get(id);
    }

    public DocTo getFull(int id) {
        LOG.info("get " + id);
        return docService.getFullByUserName(id, AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreementByUsername() {
        LOG.info("getAllAgreement");
        return AuthorizedUser.hasRole("ADMIN") ?
                docService.getAllAgreement() : docService.getAllAgreementByUsername(AuthorizedUser.getUserName());
    }

    public List<Doc> getAllAgreedByUsername() {
        LOG.info("getAllAgreed");
        return AuthorizedUser.hasRole("ADMIN") ?
                docService.getAllRegistered() : docService.getAllAgreedByUsername(AuthorizedUser.getUserName());
    }

    public List<Doc> getAllRegisteredByUsername() {
        LOG.info("getAllRegistered");
        return AuthorizedUser.hasRole("ADMIN") ?
                docService.getAllRegistered() : docService.getAllRegisteredByUsername(AuthorizedUser.getUserName());
    }

    public List<DocNumberTo> getRegNumbers() {
        LOG.info("getRegNumbers");
        return docService.getRegNumbers();
    }

    public List<Doc> getAll() {
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

    public DocTo returnDocAgreement(int id, String targetUserName, String comment) {
        LOG.info("returnDocAgreement " + id);
        return docService.returnDocAgreement(id, targetUserName, AuthorizedUser.getUserName(), comment);
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
