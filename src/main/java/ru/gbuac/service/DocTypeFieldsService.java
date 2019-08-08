package ru.gbuac.service;

import javassist.NotFoundException;
import ru.gbuac.model.DocTypeFields;

import java.util.List;

public interface DocTypeFieldsService {
    DocTypeFields get(int id) throws NotFoundException;

    DocTypeFields save(DocTypeFields docTypeFields);

    DocTypeFields update(DocTypeFields docTypeFields, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;
}
