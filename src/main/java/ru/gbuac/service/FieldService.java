package ru.gbuac.service;

import javassist.NotFoundException;
import ru.gbuac.model.Field;

import java.util.List;

public interface FieldService {
    Field get(int id) throws NotFoundException;

    List<Field> getAll();

    Field save(Field field);

    Field update(Field field, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;
}
