package ru.gbuac.controller.doc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gbuac.model.Doc;
import ru.gbuac.model.DocType;
import ru.gbuac.service.DocService;
import ru.gbuac.service.DocTypeService;

import java.util.List;

import static ru.gbuac.util.ValidationUtil.assureIdConsistent;
import static ru.gbuac.util.ValidationUtil.checkNew;

public abstract class AbstractDocRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    protected DocService docService;

    public Doc get(int id) {
        LOG.info("get " + id);
        return docService.get(id);
    }

    public List<Doc> getAll() {
        LOG.info("getAll");
        return docService.getAll();
    }

    public Doc create(Doc doc) {
        LOG.info("create " + doc);
        checkNew(doc);
        return docService.save(doc);
    }

    public Doc update(Doc doc, int id) {
        LOG.info("update " + doc);
        assureIdConsistent(doc, id);
        return docService.update(doc, id);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        docService.delete(id);
    }
}
