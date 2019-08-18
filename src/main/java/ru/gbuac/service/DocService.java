package ru.gbuac.service;

import ru.gbuac.model.Doc;
import ru.gbuac.to.DocTo;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface DocService {
    Doc get(int id) throws NotFoundException;

    DocTo getFullByUserName(int id, String userName) throws NotFoundException;

    List<Doc> getAllAgreementByUsername(String userName);

    List<Doc> getAll();

    DocTo save(DocTo doc, String userName);

    DocTo update(DocTo docTo, int id, String userName) throws NotFoundException;

    void delete(int id) throws NotFoundException;
}
