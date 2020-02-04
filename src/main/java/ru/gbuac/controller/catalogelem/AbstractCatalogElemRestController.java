package ru.gbuac.controller.catalogelem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gbuac.model.CatalogElem;
import ru.gbuac.service.CatalogElemService;
import ru.gbuac.to.CatalogElemTo;
import java.util.List;
import static ru.gbuac.util.ValidationUtil.assureIdConsistent;
import static ru.gbuac.util.ValidationUtil.checkNew;

public abstract class AbstractCatalogElemRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private CatalogElemService catalogElemService;

    public CatalogElem get(int id, int catalogId) {
        LOG.info("get " + id);
        return catalogElemService.get(id, catalogId);
    }

    public List<CatalogElemTo> getAll(int catalogId) {
        LOG.info("getAll");
        return catalogElemService.getAll(catalogId);
    }

    public List<CatalogElemTo> getAllByParentCatalogElem(int catalogId, int idParentCatalogElem) {
        LOG.info("getAllByParentCatalogElem");
        return catalogElemService.getAllByParentCatalogElem(catalogId, idParentCatalogElem);
    }

    public CatalogElem create(CatalogElem catalogElem, int catalogId) {
        LOG.info("create " + catalogElem + " at catalogId=" + catalogId);
        checkNew(catalogElem);
        return catalogElemService.save(catalogElem, catalogId);
    }

    public CatalogElem update(CatalogElem catalogElem, int id, int catalogId) {
        LOG.info("update " + catalogElem + " at catalogId=" + catalogId);
        assureIdConsistent(catalogElem, id);
        return catalogElemService.update(catalogElem, id, catalogId);
    }

    public void delete(int id, int catalogId) {
        LOG.info("delete " + id);
        catalogElemService.delete(id, catalogId);
    }
}
