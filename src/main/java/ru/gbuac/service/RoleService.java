package ru.gbuac.service;

import ru.gbuac.model.Role;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface RoleService {
    Role get(int id) throws NotFoundException;

    List<Role> getAll();

    Role save(Role role);

    Role update(Role role, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;
}
