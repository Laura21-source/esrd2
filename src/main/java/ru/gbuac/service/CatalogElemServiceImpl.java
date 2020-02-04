package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.CatalogElemRepository;
import ru.gbuac.dao.CatalogRepository;
import ru.gbuac.model.Catalog;
import ru.gbuac.model.CatalogElem;
import ru.gbuac.to.CatalogElemTo;
import ru.gbuac.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class CatalogElemServiceImpl implements CatalogElemService{

    @Autowired
    private CatalogElemRepository catalogElemRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public CatalogElem get(int id, int catalogId) throws NotFoundException {
        CatalogElem catalogElem = catalogElemRepository.findById(id).orElse(null);
        return checkNotFoundWithId(catalogElem != null && catalogElem.getCatalog().getId() == catalogId ? catalogElem : null, id);
    }

    @Override
    public List<CatalogElemTo> getAll(int catalogId) {
        List<CatalogElem> catalogElems = catalogElemRepository.getAll(catalogId);
        List<CatalogElemTo> catalogElemTos = new ArrayList<>();
        for (CatalogElem c : catalogElems) {
            catalogElemTos.add(new CatalogElemTo(c.getId(), null, c.getValueInt(),
                    c.getValueStr(), c.getValueStrPreposition(), c.getCatalog().getId()));
        }

        return catalogElemTos;
    }

    @Override
    public List<CatalogElemTo> getAllByParentCatalogElem(int catalogId, int idParentCatalogElem) {
        List<CatalogElem> catalogElems = catalogElemRepository.getAllByParentCatalogElem(catalogId, idParentCatalogElem);
        List<CatalogElemTo> catalogElemTos = new ArrayList<>();
        for (CatalogElem c : catalogElems) {
            catalogElemTos.add(new CatalogElemTo(c.getId(), null, c.getValueInt(),
                    c.getValueStr(), c.getValueStrPreposition(), c.getCatalog().getId()));
        }

        return catalogElemTos;
    }

    @Override
    public CatalogElem save(CatalogElemTo catalogElemTo, int catalogId) {
        Assert.notNull(catalogElemTo, "catalogElem must not be null");
        Catalog catalog = catalogRepository.findById(catalogId).orElse(null);
        CatalogElem parentCatalogElem = null;
        if (catalogElemTo.getIdParentCatalogElem() != null) {
            parentCatalogElem = catalogElemRepository.findById(catalogElemTo.getIdParentCatalogElem()).orElse(null);
        } else {
            parentCatalogElem = Objects.requireNonNull(catalogElemRepository.getAll(catalogId).stream().findFirst().orElse(null)).getIdParentCatalogElem();
        }

        CatalogElem newCatalogElem = new CatalogElem(null, parentCatalogElem, catalogElemTo.getValueInt(), catalogElemTo.getValueStr(),
                catalogElemTo.getValueStrPreposition(), null, catalog);

        return catalogElemRepository.save(newCatalogElem);
    }

    @Override
    public CatalogElem update(CatalogElemTo catalogElemTo, int id, int catalogId) throws NotFoundException {
        Assert.notNull(catalogElemTo, "catalogElem must not be null");
        Catalog catalog = catalogRepository.findById(catalogId).orElse(null);
        CatalogElem parentCatalogElem = null;
        if (catalogElemTo.getIdParentCatalogElem() != null) {
            parentCatalogElem = catalogElemRepository.findById(catalogElemTo.getIdParentCatalogElem()).orElse(null);
        } else {
            parentCatalogElem = Objects.requireNonNull(catalogElemRepository.getAll(catalogId).stream().findFirst().orElse(null)).getIdParentCatalogElem();
        }
        CatalogElem updatedCatalogElem = new CatalogElem(id, parentCatalogElem, catalogElemTo.getValueInt(), catalogElemTo.getValueStr(),
                catalogElemTo.getValueStrPreposition(), null, catalog);

        return checkNotFoundWithId(catalogElemRepository.save(updatedCatalogElem), id);
    }

    @Override
    public void delete(int id, int catalogElem) throws NotFoundException {
        Assert.notNull(id, "catalogElem must not be null");
        checkNotFoundWithId(catalogElemRepository.delete(id, catalogElem) != 0, id);
    }
}
