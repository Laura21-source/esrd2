package ru.gbuac.controller.department;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gbuac.model.Department;

import javax.validation.Valid;
import java.util.List;

public class DepartmentRestController extends AbstractDepartmentRestController{

    static final String REST_URL = "/rest/profile/departments";

    @Override
    @GetMapping
    public List<Department> getAll(@PathVariable("depId") int resultId) {
        return super.getAll(resultId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Department updateOrCreate(@Valid @RequestBody Department department) {
        if (department.isNew()) {
            return super.create(department);
        } else {
            return super.update(department, department.getId());
        }
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }
}
