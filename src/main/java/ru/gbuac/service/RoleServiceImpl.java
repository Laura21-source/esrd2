package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.RoleRepository;
import ru.gbuac.model.Role;
import ru.gbuac.util.DocAgreementUtil;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role get(int id) throws NotFoundException {
        return checkNotFoundWithId(roleRepository.findById(id).orElse(null), id);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        Assert.notNull(role, "role must not be null");
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role, int id) throws NotFoundException {
        Assert.notNull(role, "role must not be null");
        return checkNotFoundWithId(roleRepository.save(role),id);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Assert.notNull(id, "role must not be null");
        checkNotFoundWithId(roleRepository.delete(id)!= 0, id);
    }
}
