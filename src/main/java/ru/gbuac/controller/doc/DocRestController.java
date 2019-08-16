package ru.gbuac.controller.doc;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gbuac.model.Doc;
import ru.gbuac.model.DocType;
import ru.gbuac.to.DocTo;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = DocRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DocRestController extends AbstractDocRestController {
    public static final String REST_URL = "/rest/profile/docs";

    @Override
    @GetMapping(value = "/{id}")
    public DocTo getFull(@PathVariable("id") int id) {
        return super.getFull(id);
    }


    @Override
    @GetMapping
    public List<Doc> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DocTo updateOrCreate(@RequestBody DocTo docTo) {
        if (docTo.isNew()) {
            return super.create(docTo);
        } else {
            return super.update(docTo, docTo.getId());
        }
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }
}
