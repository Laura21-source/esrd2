package ru.gbuac.controller.doc;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gbuac.model.Doc;
import ru.gbuac.model.DocType;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = DocRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DocRestController extends AbstractDocRestController {
    public static final String REST_URL = "/rest/profile/docs";

    @Override
    @GetMapping(value = "/{id}")
    public Doc get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<Doc> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Doc updateOrCreate(@Valid @RequestBody Doc doc) {
        if (doc.isNew()) {
            return super.create(doc);
        } else {
            return super.update(doc, doc.getId());
        }
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }
}
