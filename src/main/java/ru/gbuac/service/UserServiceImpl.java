package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Service;
import ru.gbuac.dao.RoleRepository;
import ru.gbuac.dao.UserRepository;
import ru.gbuac.model.User;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User getByName(String name) {
        return userRepository.getByName(name);
    }

    @Override
    public int deleteLocalData(int id) {
        return userRepository.delete(id);
    }

    @Override
    public String deleteLocalDataByUserName(String name) {
        return userRepository.delete(name);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
