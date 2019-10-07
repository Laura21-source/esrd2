package ru.gbuac.service;

import ru.gbuac.model.Department;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface DepartmentService {

    Department save(Department department);

    List<Department> getAll(int depId);

    Department update(Department department, int id) throws NotFoundException;

    void delete(int id);
}
