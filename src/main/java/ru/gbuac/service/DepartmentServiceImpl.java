package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.gbuac.dao.DepartmentRepository;
import ru.gbuac.model.Department;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Department save(Department department) {
        Assert.notNull(department, "department must not be null");
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAll(int depId) {
        return departmentRepository.getAll(depId);
    }

    @Override
    public Department update(Department department, int id) throws NotFoundException {
        Assert.notNull(department, "department must not be null");
        return checkNotFoundWithId(departmentRepository.save(department),id);
    }

    @Override
    public void delete(int id) {
        Assert.notNull(id, "answer must not be null");
        checkNotFoundWithId(departmentRepository.delete(id) != 0, id);
    }
}
