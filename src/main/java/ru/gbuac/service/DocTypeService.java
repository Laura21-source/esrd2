package ru.gbuac.service;

import javassist.NotFoundException;
import ru.gbuac.model.DocType;

import java.util.List;

public interface DocTypeService {
    DocType get(int id) throws NotFoundException;

    List<DocType> getAll();

    DocType save(DocType docType);

    DocType update(DocType docType, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;
}
