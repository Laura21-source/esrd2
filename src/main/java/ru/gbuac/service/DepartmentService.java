package ru.gbuac.service;

import ru.gbuac.model.Department;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface DepartmentService {
    Department get(int id) throws NotFoundException;

    List<Department> getAll();

    Department save(Department department);

    Department update(Department department, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    List<Department> getAllTopLevelDepartment();
}
