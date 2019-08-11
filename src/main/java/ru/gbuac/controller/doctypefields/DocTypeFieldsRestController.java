package ru.gbuac.controller.doctypefields;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gbuac.model.DocTypeFields;

import java.util.List;

@RestController
@RequestMapping(value = DocTypeFieldsRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DocTypeFieldsRestController extends AbstractDocTypeFieldsRestController {
    public static final String REST_URL = "/rest/profile/doctypes/{docTypeId}/docTypeFields";

    @Override
    @GetMapping
    public List<DocTypeFields> getAll(@PathVariable("docTypeId") int docTypeId) {
        List<DocTypeFields> docTypeFields = super.getAll(docTypeId);
        for (DocTypeFields docTypeField : docTypeFields) {
            docTypeField.setId(null);
            docTypeField.getField().setId(null);
        }
        return docTypeFields;
    }
}
