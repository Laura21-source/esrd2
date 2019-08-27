package ru.gbuac.repository;

import org.junit.Assert;
import org.junit.Test;
import ru.gbuac.dao.UserRepository;
import ru.gbuac.model.User;

import javax.transaction.Transactional;
import java.util.List;

public class UserRepositoryTest extends AbsractRepositoryTest {

    @Test
    public void create() {
        User user = new User("user1", "Pupkin", "Vasya", "Vasilievich", "vasya@tut.ru", "123456", "dvornic");

        User savedUser = userRepository.save(user);
        user.setId(savedUser.getId());
        Assert.assertEquals(user, savedUser);
    }

    /*
    @Test
    public void read() {
        User user = new User();
        user.getName();
        user.getLastname();
        user.getEmail();
        user.getPhone();
        user.getPosition();
        user.getRoles();

    }

    @Test
    public void update() {
        User user = new User();
        user.setName(new User());
        user.setLastname();
        user.setPatronym();
        user.setPhone();
        user.setPosition();
        user.setRoles();
    }

    @Test
    public void delete() {
        User user = new User();

    }

    @Test
    public void getAll() {
        User user = new User();
        List<User> users;
    }
    /
 */

}
