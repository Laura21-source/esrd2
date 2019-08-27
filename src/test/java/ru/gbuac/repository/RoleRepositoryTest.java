package ru.gbuac.repository;

import org.junit.Assert;
import org.junit.Test;
import ru.gbuac.model.Role;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoleRepositoryTest extends AbsractRepositoryTest {
    @Test
    public void get() {
        Role savedRole = roleRepository.save(getRole());
        Role returnedRoles = roleRepository.findById(savedRole.getId()).orElse(null);
        Assert.assertEquals(savedRole, returnedRoles);
    }

    @Test
    public void getAll() {
        Role savedRole1 = roleRepository.save(getRole());
        Role savedRole2 = roleRepository.save(getRole2());
        List<Role> returnedRoles = roleRepository.findAll();
        Assert.assertEquals(Arrays.asList(savedRole1, savedRole2), returnedRoles);
    }

    @Test
    public void create() {
        Role role = getRole();
        Role savedRole = roleRepository.save(role);
        Assert.assertEquals(role, savedRole);
    }

    @Test
    public void update() {
        Role savedRole = roleRepository.save(getRole());
        savedRole.setName("председатель");
        Role updatedRole = roleRepository.save(savedRole);
        Assert.assertEquals(updatedRole, savedRole);
    }

    @Test
    public void delete() {
        Role savedRole = roleRepository.save(getRole());
        roleRepository.delete(savedRole.getId());
        List<Role> returnedRoles = roleRepository.findAll();
        Assert.assertEquals(Collections.emptyList(), returnedRoles);

    }
}
