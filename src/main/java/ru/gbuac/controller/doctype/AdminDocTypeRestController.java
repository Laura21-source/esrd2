package ru.gbuac.controller.doctype;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gbuac.model.DocType;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = AdminDocTypeRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDocTypeRestController extends AbstractDocTypeRestController {
    public static final String REST_URL = "/rest/admin/doctypes";

    @Override
    @GetMapping(value = "/{id}")
    public DocType get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<DocType> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DocType updateOrCreate(@Valid @RequestBody DocType docType) {
        if (docType.isNew()) {
            return super.create(docType);
        } else {
            return super.update(docType, docType.getId());
        }
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }
}
