package ru.gbuac.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.gbuac.model.Organization;
import ru.gbuac.model.User;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllLdapUsers();

    User getByName(String name) throws NotFoundException;

    User get(int id) throws NotFoundException;

    List<User> getAll();

    User save(User user);

    User update(User user, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    void deleteByName(String name) throws NotFoundException;

    void sinchronizeUsersByLdap();

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
