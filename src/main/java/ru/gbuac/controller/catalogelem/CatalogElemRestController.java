package ru.gbuac.controller.catalogelem;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gbuac.model.CatalogElem;
import ru.gbuac.to.CatalogElemTo;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = CatalogElemRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CatalogElemRestController extends AbstractCatalogElemRestController {
    public static final String REST_URL = "/rest/profile/catalogs/{catalogId}/elems";

    @Override
    @GetMapping
    public List<CatalogElemTo> getAll(@PathVariable("catalogId") int catalogId) {
        return super.getAll(catalogId);
    }

    @Override
    @GetMapping(value = "/parent/{parentCatalogElemId}")
    public List<CatalogElemTo> getAllByParentCatalogElem(@PathVariable("catalogId") int catalogId,
                                                         @PathVariable("parentCatalogElemId") int idParentCatalogElem) {
        return super.getAllByParentCatalogElem(catalogId, idParentCatalogElem);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public CatalogElem updateOrCreate(@Valid @RequestBody CatalogElem catalogElem, @PathVariable("catalogId") int catalogId) {
        if (catalogElem.isNew()) {
            return super.create(catalogElem, catalogId);
        } else {
            return super.update(catalogElem, catalogElem.getId(), catalogId);
        }
    }
}
