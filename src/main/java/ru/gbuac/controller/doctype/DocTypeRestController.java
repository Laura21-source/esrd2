package ru.gbuac.controller.doctype;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gbuac.model.DocType;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = DocTypeRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DocTypeRestController extends AbstractDocTypeRestController {
    public static final String REST_URL = "/rest/profile/doctypes";

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


    @Override
    @GetMapping(value = "/filtered")
    public List<DocType> getAllFiltered() {
        return super.getAllFiltered();
    }
}
