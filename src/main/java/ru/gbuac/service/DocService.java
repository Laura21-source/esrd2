package ru.gbuac.service;

import ru.gbuac.model.Doc;
import ru.gbuac.to.DocTo;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface DocService {
    Doc get(int id) throws NotFoundException;

    DocTo getFull(int id) throws NotFoundException;

    List<Doc> getAll();

    DocTo save(DocTo doc);

    DocTo update(DocTo docTo, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;
}
