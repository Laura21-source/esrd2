package ru.gbuac.controller.doctype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gbuac.model.DocType;
import ru.gbuac.service.DocTypeService;

import java.util.List;

public abstract class AbstractDocTypeRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    protected DocTypeService docTypeService;

    public DocType get(int id) {
        LOG.info("get " + id);
        return docTypeService.get(id);
    }

    public List<DocType> getAll() {
        LOG.info("getAll");
        return docTypeService.getAll();
    }

}
