package ru.gbuac.service;

import javassist.NotFoundException;
import ru.gbuac.model.GroupedField;

import java.util.List;

public interface GroupedFieldService {
    GroupedField get(int id) throws NotFoundException;

    List<GroupedField> getAll();

    GroupedField save(GroupedField groupedField);

    GroupedField update(GroupedField groupedField, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;
}
