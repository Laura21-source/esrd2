package ru.gbuac.controller.department;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gbuac.model.Department;
import ru.gbuac.service.DepartmentService;

import java.util.List;

import static ru.gbuac.util.ValidationUtil.assureIdConsistent;
import static ru.gbuac.util.ValidationUtil.checkNew;

public abstract class AbstractDepartmentRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    DepartmentService departmentService;


    public List<Department> getAll(int depId) {
        LOG.info("getAll");
        return departmentService.getAll(depId);
    }

     public Department create(Department department) {
        LOG.info("create " + department);
        checkNew(department);
        return departmentService.save(department);
    }

    public Department update(Department department, int id) {
        LOG.info("update " + department);
        assureIdConsistent(department, id);
        return departmentService.update(department, id);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        departmentService.delete(id);
    }
}
