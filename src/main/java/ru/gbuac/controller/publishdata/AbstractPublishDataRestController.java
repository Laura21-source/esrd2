package ru.gbuac.controller.publishdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gbuac.model.PublishData;
import ru.gbuac.service.PublishDataService;

public abstract class AbstractPublishDataRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    PublishDataService publishDataService;

    PublishData publish(int docId, String rootPath) {
        LOG.info("publish");
        return publishDataService.publish(docId, rootPath);
    }

}
