package ru.gbuac.service;

import ru.gbuac.model.Doc;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface DocService {
    Doc get(int id) throws NotFoundException;

    List<Doc> getAll();

    Doc save(Doc doc);

    Doc update(Doc doc, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;
}
