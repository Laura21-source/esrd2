package ru.gbuac.controller.doc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gbuac.model.Doc;
import ru.gbuac.model.DocType;
import ru.gbuac.service.DocService;
import ru.gbuac.service.DocTypeService;
import ru.gbuac.to.DocTo;

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

    public DocTo getFull(int id) {
        LOG.info("get " + id);
        return docService.getFull(id);
    }

    public List<Doc> getAll() {
        LOG.info("getAll");
        return docService.getAll();
    }

    public DocTo create(DocTo docTo) {
        LOG.info("create " + docTo);
        checkNew(docTo);
        return docService.save(docTo);
    }

    public DocTo update(DocTo docTo, int id) {
        LOG.info("update " + docTo);
        assureIdConsistent(docTo, id);
        return docService.update(docTo, id);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        docService.delete(id);
    }
}
