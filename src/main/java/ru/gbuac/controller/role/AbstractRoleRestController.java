package ru.gbuac.controller.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gbuac.model.Role;
import ru.gbuac.service.RoleService;

import java.util.List;

import static ru.gbuac.util.ValidationUtil.assureIdConsistent;
import static ru.gbuac.util.ValidationUtil.checkNew;

public abstract class AbstractRoleRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RoleService roleService;

    public Role get(int id) {
        LOG.info("get " + id);
        return roleService.get(id);
    }

    public List<Role> getAll() {
        LOG.info("getAll");
        return roleService.getAll();
    }

    public Role create(Role role) {
        LOG.info("create " + role);
        checkNew(role);
        return roleService.save(role);
    }

    public Role update(Role role, int id) {
        LOG.info("update " + role);
        assureIdConsistent(role, id);
        return roleService.update(role, id);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        roleService.delete(id);
    }
}
