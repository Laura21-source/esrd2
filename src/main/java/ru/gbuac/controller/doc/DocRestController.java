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
    @GetMapping(value = "/distribution")
    public List<DocItemTo> getAllDistribution() {
        return super.getAllDistribution();
    }

    @Override
    @GetMapping(value = "/distributed")
    public List<DocItemTo> getAllDistributed() {
        return super.getAllDistributed();
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
