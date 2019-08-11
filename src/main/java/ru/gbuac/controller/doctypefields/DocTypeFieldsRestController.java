package ru.gbuac.controller.doctypefields;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gbuac.model.DocTypeFields;
import ru.gbuac.model.Field;

import java.util.List;

@RestController
@RequestMapping(value = DocTypeFieldsRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DocTypeFieldsRestController extends AbstractDocTypeFieldsRestController {
    public static final String REST_URL = "/rest/profile/doctypes/{docTypeId}/fields";

    @Override
    @GetMapping
    public List<DocTypeFields> getAll(@PathVariable("docTypeId") int docTypeId) {
        List<DocTypeFields> docTypeFields = super.getAll(docTypeId);
        for (DocTypeFields docTypeField : docTypeFields) {
            docTypeField.setId(null);
            clearFields(docTypeField.getField());
        }
        return docTypeFields;
    }

    private void clearFields (Field field) {
        for (Field childField : field.getChildField()) {
            clearFields(childField);
        }
        field.setId(null);
    }
}
