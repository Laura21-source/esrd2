package ru.gbuac.service;

import ru.gbuac.model.Role;
import ru.gbuac.model.User;

import java.util.List;

public interface RoleService {

    int delete(int id);

    List<Role> getAllRoles();

    String deleteRoleByUserName(String name);

    Role save(Role role);

    User getRolesByUsername(String name);
}
