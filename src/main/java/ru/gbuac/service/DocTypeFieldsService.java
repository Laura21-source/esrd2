package ru.gbuac.service;

import ru.gbuac.model.DocTypeFields;
import ru.gbuac.to.DocTypeFieldsTo;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface DocTypeFieldsService {
    DocTypeFields get(int id, int docTypeId) throws NotFoundException;

    List<DocTypeFields> getAll(int docTypeId);

    List<DocTypeFieldsTo> getAllFull(int docTypeId);

    DocTypeFields save(DocTypeFields docTypeFields);

    DocTypeFields update(DocTypeFields docTypeFields, int id) throws NotFoundException;

    void delete(int id, int docTypeId) throws NotFoundException;
}
