package ru.gbuac.controller.publishdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gbuac.model.PublishData;

import javax.servlet.ServletContext;

@RestController
@RequestMapping(value = PublishDataRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublishDataRestController extends AbstractPublishDataRestController {
    public static final String REST_URL = "/rest/admin/docs//publishdata";

    @Autowired
    ServletContext context;

    @PostMapping(value = "/rejectDocAgreement/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    PublishData publish(int docId) {
        return super.publish(docId, context.getRealPath("/"));
    }
}
