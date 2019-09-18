package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gbuac.dao.RoleRepository;
import ru.gbuac.dao.UserRepository;
import ru.gbuac.model.Role;
import ru.gbuac.model.User;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public int delete(int id) {
        return roleRepository.delete(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public String deleteRoleByUserName(String name) {
        return roleRepository.delete(name);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User getRolesByUsername(String name) {
        return userRepository.getByName(name);
    }
}
