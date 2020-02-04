package ru.gbuac.controller.role;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gbuac.model.Role;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = AdminRoleRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRoleRestController extends AbstractRoleRestController {
    public static final String REST_URL = "/rest/admin/roles";

    @Override
    @GetMapping(value = "/{id}")
    public Role get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<Role> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Role updateOrCreate(@Valid @RequestBody Role role) {
        if (role.isNew()) {
            return super.create(role);
        } else {
            return super.update(role, role.getId());
        }
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }
}
