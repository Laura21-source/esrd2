package ru.gbuac.controller.doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gbuac.AuthorizedUser;
import ru.gbuac.model.Doc;
import ru.gbuac.model.DocType;
import ru.gbuac.to.DocTo;
import ru.gbuac.to.PdfTo;

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
    public List<Doc> getAllAgreementByUsername() {
        return super.getAllAgreementByUsername();
    }

    @Override
    @GetMapping(value = "/agreed")
    public List<Doc> getAllAgreedByUsername() {
        return super.getAllAgreedByUsername();
    }

    @Override
    @GetMapping(value = "/registered")
    public List<Doc> getAllRegisteredByUsername() {
        return super.getAllRegisteredByUsername();
    }

    @Override
    @GetMapping
    public List<Doc> getAll() {
        return super.getAll();
    }

    @PostMapping(value = "/uploadfile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String fileUploadFile(@RequestParam("file") MultipartFile file) {
        return super.fileUploadFile(file, context.getRealPath("/"));
    }

    @PostMapping(value = "/pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PdfTo createPDF(@Valid @RequestBody DocTo docTo) {
        return super.createPDF(docTo, context.getRealPath("/"));
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
}
