package ru.gbuac.controller.publishdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gbuac.model.PublishData;

import javax.servlet.ServletContext;

@RestController
@RequestMapping(value = PublishDataRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublishDataRestController extends AbstractPublishDataRestController {
    public static final String REST_URL = "/rest/admin/publishdata";

    @Autowired
    ServletContext context;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    PublishData publish(@RequestParam("docId") int docId) {
        return super.publish(docId, context.getRealPath("/"));
    }
}
