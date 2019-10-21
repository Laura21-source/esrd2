package ru.gbuac.controller.doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gbuac.model.Doc;
import ru.gbuac.model.User;
import ru.gbuac.to.*;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = DocRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DocRestController extends AbstractDocRestController {
    public static final String REST_URL = "/rest/profile/docs";

    @Autowired
    ServletContext context;

    @Override
    @GetMapping(value = "/{id}")
    public DocTo getFull(@PathVariable("id") int id) {
        return super.getFull(id);
    }

    @Override
    @GetMapping(value = "/agreement")
    public List<Doc> getAllAgreement() {
        return super.getAllAgreement();
    }

    @Override
    @GetMapping(value = "/agreementMoreDeadlineByUserName")
    public List<Doc> getAllAgreementMoreDeadlineByUserName() {
        return super.getAllAgreementMoreDeadlineByUserName();
    }

    @Override
    @GetMapping(value = "/agreementMoreDeadlineByUserName/count")
    public Integer getCountAgreementMoreDeadlineByUserName() {
        return super.getCountAgreementMoreDeadlineByUserName();
    }

    @Override
    @GetMapping(value = "/agreementLessDeadlineByUserName")
    public List<Doc> getAllAgreementLessDeadlineByUserName() {
        return super.getAllAgreementLessDeadlineByUserName();
    }

    @Override
    @GetMapping(value = "/agreementLessDeadlineByUserName/count")
    public Integer getCountAgreementLessDeadlineByUserName() {
        return super.getCountAgreementLessDeadlineByUserName();
    }

    @Override
    @GetMapping(value = "/agreementMoreDeadlineByDepartment")
    public List<Doc> getAllAgreementMoreDeadlineByDepartment() {
        return super.getAllAgreementMoreDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/agreementMoreDeadlineByDepartment/count")
    public Integer getCountAgreementMoreDeadlineByDepartment() {
        return super.getCountAgreementMoreDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/agreementLessDeadlineByDepartment")
    public List<Doc> getAllAgreementLessDeadlineByDepartment() {
        return super.getAllAgreementLessDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/agreementLessDeadlineByDepartment/count")
    public Integer getCountAgreementLessDeadlineByDepartment() {
        return super.getCountAgreementLessDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/agreed")
    public List<Doc> getAllAgreed() {
        return super.getAllAgreed();
    }

    @Override
    @GetMapping(value = "/registered")
    public List<Doc> getAllRegistered() {
        return super.getAllRegistered();
    }

    @Override
    @GetMapping(value = "/inwork")
    public List<DocItemTo> getAllInWorkByUserName() {
        return super.getAllInWorkByUserName();
    }

    @Override
    @GetMapping(value = "/inwork/count")
    public Integer getCountInWorkByUserName() {
        return super.getCountInWorkByUserName();
    }

    @Override
    @GetMapping(value = "/inworkMoreDeadlineByUserName")
    public List<DocItemTo> getAllInWorkMoreDeadlineByUserName() {
        return super.getAllInWorkMoreDeadlineByUserName();
    }

    @Override
    @GetMapping(value = "/inworkMoreDeadlineByUserName/count")
    public Integer getCountInWorkMoreDeadlineByUserName() {
        return super.getCountInWorkMoreDeadlineByUserName();
    }

    @Override
    @GetMapping(value = "/inworkLessDeadlineByUserName")
    public List<DocItemTo> getAllInWorkLessDeadlineByUserName() {
        return super.getAllInWorkLessDeadlineByUserName();
    }

    @Override
    @GetMapping(value = "/inworkLessDeadlineByUserName/count")
    public Integer geCountInWorkLessDeadlineByUserName() {
        return super.geCountInWorkLessDeadlineByUserName();
    }

    @Override
    @GetMapping(value = "/inworkMoreDeadlineByDepartment")
    public List<DocItemTo> getAllInWorkMoreDeadlineByDepartment() {
        return super.getAllInWorkMoreDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/inworkMoreDeadlineByDepartment/count")
    public Integer getCountInWorkMoreDeadlineByDepartment() {
        return super.getCountInWorkMoreDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/inworkLessDeadlineByDepartment")
    public List<DocItemTo> getAllInWorkLessDeadlineByDepartment() {
        return super.getAllInWorkLessDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/inworkLessDeadlineByDepartment/count")
    public Integer getCountInWorkLessDeadlineByDepartment() {
        return super.getCountInWorkLessDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/distribution")
    public List<DocItemTo> getAllDistribution() {
        return super.getAllDistribution();
    }

    @Override
    @GetMapping(value = "/distribution/count")
    public Integer getCountDistribution() {
        return super.getCountDistribution();
    }

    @Override
    @GetMapping(value = "/distributionMoreDeadlineByChiefUserName")
    public List<DocItemTo> getAllDistributionMoreDeadlineByChiefUserName() {
        return super.getAllDistributionMoreDeadlineByChiefUserName();
    }

    @Override
    @GetMapping(value = "/distributionMoreDeadlineByChiefUserName/count")
    public Integer getCountDistributionMoreDeadlineByChiefUserName() {
        return super.getCountDistributionMoreDeadlineByChiefUserName();
    }

    @Override
    @GetMapping(value = "/distributionLessDeadlineByChiefUserName")
    public List<DocItemTo> getAllDistributionLessDeadlineByChiefUserName() {
        return super.getAllDistributionLessDeadlineByChiefUserName();
    }

    @Override
    @GetMapping(value = "/distributionLessDeadlineByChiefUserName/count")
    public Integer getCountDistributionLessDeadlineByChiefUserName() {
        return super.getCountDistributionLessDeadlineByChiefUserName();
    }

    @Override
    @GetMapping(value = "/distributionMoreDeadlineByDepartment")
    public List<DocItemTo> getAllDistributionMoreDeadlineByDepartment() {
        return super.getAllDistributionMoreDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/distributionMoreDeadlineByDepartment/count")
    public Integer getCountDistributionMoreDeadlineByDepartment() {
        return super.getCountDistributionMoreDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/distributionLessDeadlineByDepartment")
    public List<DocItemTo> getAllDistributionLessDeadlineByDepartment() {
        return super.getAllDistributionLessDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/distributionLessDeadlineByDepartment/count")
    public Integer getCountDistributionLessDeadlineByDepartment() {
        return super.getCountDistributionLessDeadlineByDepartment();
    }

    @Override
    @GetMapping(value = "/distributed")
    public List<DocItemTo> getAllDistributed() {
        return super.getAllDistributed();
    }

    @Override
    @GetMapping(value = "/atThisMounthOnControl")
    public List<DocItemTo> getAllAtThisMounthOnControl() {
        return super.getAllAtThisMounthOnControl();
    }

    @Override
    public Integer getCountAtThisMounthOnControl() {
        return super.getCountAtThisMounthOnControl();
    }

    @Override
    @GetMapping(value = "/atThisMounthOnControlCompletedInTime")
    public List<DocItemTo> getAllAtThisMounthOnControlCompletedInTime() {
        return super.getAllAtThisMounthOnControlCompletedInTime();
    }

    @Override
    @GetMapping(value = "/atThisMounthOnControlCompletedInTime/count")
    public Integer getCountAtThisMounthOnControlCompletedInTime() {
        return super.getCountAtThisMounthOnControlCompletedInTime();
    }

    @Override
    @GetMapping(value = "/atThisMounthOnControlCompletedAfterTime")
    public List<DocItemTo> getAllAtThisMounthOnControlCompletedAfterTime() {
        return super.getAllAtThisMounthOnControlCompletedAfterTime();
    }

    @Override
    @GetMapping(value = "/atThisMounthOnControlCompletedAfterTime/count")
    public Integer getCountAtThisMounthOnControlCompletedAfterTime() {
        return super.getCountAtThisMounthOnControlCompletedAfterTime();
    }

    @Override
    @GetMapping(value = "/atThisMounthOnControlNotCompleted")
    public List<DocItemTo> getAllAtThisMounthOnControlNotCompleted() {
        return super.getAllAtThisMounthOnControlNotCompleted();
    }

    @Override
    @GetMapping(value = "/atThisMounthOnControlNotCompleted/count")
    public Integer getCountAtThisMounthOnControlNotCompleted() {
        return super.getCountAtThisMounthOnControlNotCompleted();
    }

    @Override
    @GetMapping(value = "/regnumbers")
    public List<DocNumberTo> getRegNumbers() {
        return super.getRegNumbers();
    }


    @Override
    @GetMapping
    public List<DocItemTo> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DocTo updateOrCreate(@Valid @RequestBody DocTo docTo) {
        if (docTo.isNew()) {
            return super.create(docTo, context.getRealPath("/"));
        } else {
            return super.update(docTo, docTo.getId(), context.getRealPath("/"));
        }
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PostMapping(value = "/rejectDocAgreement/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DocTo rejectDocAgreement(@PathVariable("id")int id, @RequestParam("comment") String comment) {
        return super.rejectDocAgreement(id, comment);
    }

    @Override
    @PostMapping(value = "/executorUsersList/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<User> saveExecutorUsersList(@PathVariable("id") int id, @Valid @RequestBody List<User> executorUsers) {
        return super.saveExecutorUsersList(id, executorUsers);
    }

    @PostMapping(value = "/uploadfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileTo uploadFile(@RequestParam("inputFile") MultipartFile inputFile) {
        return super.uploadFile(inputFile, context.getRealPath("/"));
    }

    @PostMapping(value = "/docx", consumes = MediaType.APPLICATION_JSON_VALUE)
    public FileTo createDocx(@Valid @RequestBody DocTo docTo) {
        return super.createDocx(docTo, context.getRealPath("/"));
    }

    @PostMapping(value = "/pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
    public FileTo createPDF(@Valid @RequestBody DocTo docTo) {
        return super.createPDF(docTo, context.getRealPath("/"));
    }

}
