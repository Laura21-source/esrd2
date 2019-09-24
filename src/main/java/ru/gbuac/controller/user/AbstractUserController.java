package ru.gbuac.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gbuac.model.User;
import ru.gbuac.service.UserService;

import java.util.List;

public class AbstractUserController {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    List<String> getAllLdapUsers(){
        LOG.info("getAllLdapUsers");
        return userService.getAllLdapUsers();
    }

    User getByName(String name) {
        LOG.info("getByName");
        return userService.getByName(name);
    }

    List<User> getAll() {
        LOG.info("getAll");
        return userService.getAll();
    }

    User save(User user) {
        LOG.info("save");
        return userService.save(user);
    }

    User update(User user, int id) {
        LOG.info("update");
        return userService.update(user, id);
    }

    void delete(int id) {
        LOG.info("delete");
        userService.delete(id);
    }
}
