package ru.gbuac.controller.catalogelem;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gbuac.model.CatalogElem;
import java.util.List;

@RestController
@RequestMapping(value = CatalogElemRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CatalogElemRestController extends AbstractCatalogElemRestController {
    public static final String REST_URL = "/rest/profile/catalogs/{catalogId}/elems";

    @Override
    @GetMapping
    public List<CatalogElem> getAll(@PathVariable("catalogId") int catalogId) {
        return super.getAll(catalogId);
    }
}
