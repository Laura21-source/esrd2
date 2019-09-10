package ru.gbuac.controller.docagreement;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gbuac.controller.doctypefields.DocTypeFieldsRestController;
import ru.gbuac.to.DocAgreementTo;

import java.util.List;

@RestController
@RequestMapping(value = DocAgreementRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DocAgreementRestController extends AbstractDocAgreementRestController {
    public static final String REST_URL = "/rest/profile/docs/{docId}/agreement";

    @Override
    @GetMapping(value = "/list")
    public List<DocAgreementTo> getAgreementList(@PathVariable("docId") int docId) {
        return super.getAgreementList(docId);
    }
}
