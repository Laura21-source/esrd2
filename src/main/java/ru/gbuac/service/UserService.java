package ru.gbuac.service;

import ru.gbuac.model.User;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    public List<String> getAllLdapUsers();

    User getByName(String name) throws NotFoundException;

    List<User> getAll();

    User save(User user);

    User update(User user, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

}
