package ru.gbuac.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gbuac.model.User;
import ru.gbuac.service.UserService;
import ru.gbuac.to.UserTo;

import java.util.List;

import static ru.gbuac.util.ValidationUtil.assureIdConsistent;
import static ru.gbuac.util.ValidationUtil.checkNew;

public class AbstractUserRestController {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    List<User> getAllLdapUsers() {
        LOG.info("getAllLdapUsers");
        return userService.getAllLdapUsers();
    }

    public void sinchronizeUsersByLdap() {
        LOG.info("sinchronizeUsersByLdap");
        userService.sinchronizeUsersByLdap();
    }

    User getByName(String name) {
        LOG.info("getByName");
        return userService.getByName(name);
    }

    public User get(int id) {
        LOG.info("get " + id);
        return userService.get(id);
    }

    List<UserTo> getAll() {
        LOG.info("getAll");
        return userService.getAll();
    }


    public User create(User user) {
        LOG.info("create " + user);
        checkNew(user);
        return userService.save(user);
    }

    public User update(User user, int id) {
        LOG.info("update " + user);
        assureIdConsistent(user, id);
        return userService.update(user, id);
    }

    void delete(int id) {
        LOG.info("delete");
        userService.delete(id);
    }

    void deleteByName( String name) {
       LOG.info("deleteByName");
       userService.deleteByName(name);
    }
}

