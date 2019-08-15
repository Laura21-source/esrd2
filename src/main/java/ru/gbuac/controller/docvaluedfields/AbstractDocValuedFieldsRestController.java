package ru.gbuac.controller.docvaluedfields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gbuac.model.Doc;
import ru.gbuac.model.DocTypeFields;
import ru.gbuac.model.DocValuedFields;
import ru.gbuac.service.DocTypeFieldsService;
import ru.gbuac.service.DocValuedFieldsService;
import ru.gbuac.to.DocTypeFieldsTo;

import java.util.List;

import static ru.gbuac.util.ValidationUtil.assureIdConsistent;
import static ru.gbuac.util.ValidationUtil.checkNew;

public abstract class AbstractDocValuedFieldsRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private DocValuedFieldsService docValuedFieldsService;

    public DocValuedFields get(int id, int docId) {
        LOG.info("get " + id);
        return docValuedFieldsService.get(id, docId);
    }

    public List<DocValuedFields> getAll(int docId) {
        LOG.info("getAll");
        return docValuedFieldsService.getAll(docId);
    }

    public DocValuedFields create(DocValuedFields docValuedFields) {
        LOG.info("create " + docValuedFields);
        checkNew(docValuedFields);
        return docValuedFieldsService.save(docValuedFields);
    }

    public DocValuedFields update(DocValuedFields docValuedFields, int id) {
        LOG.info("update " + docValuedFields);
        assureIdConsistent(docValuedFields, id);
        return docValuedFieldsService.update(docValuedFields, id);
    }

    public void delete(int id, int docValuedFieldsId) {
        LOG.info("delete " + id);
        docValuedFieldsService.delete(id, docValuedFieldsId);
    }
}
