package ru.gbuac.repository;

import org.junit.Assert;
import org.junit.Test;
import ru.gbuac.dao.UserRepository;
import ru.gbuac.model.User;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserRepositoryTest extends AbsractRepositoryTest {

    @Test
    public void get() {
        User savedUser = userRepository.save(new User("user1", "Pupkin", "Vasya", "Vasilievich", "vasya@tut.ru", "123456", "dvornic"));
        User returnedUser = userRepository.findById(savedUser.getId()).orElse(null);
        Assert.assertEquals(savedUser, returnedUser);
    }

    @Test
    public void getAll() {
        User savedUser1 = userRepository.save(new User("user1", "Pupkin", "Vasya", "Vasilievich", "vasya@tut.ru", "123456", "dvornic"));
        User savedUser2 = userRepository.save(new User("user2", "Sokolov", "Eugeny", "Ivanovich", "vasya@tut.ru", "123456", "dvornic"));
        List<User> returnedUsers = userRepository.findAll();
        Assert.assertEquals(Arrays.asList(savedUser1, savedUser2), returnedUsers);
    }

    @Test
    public void create() {
        User user = new User("user1", "Pupkin", "Vasya", "Vasilievich", "vasya@tut.ru", "123456", "dvornic");
        User savedUser = userRepository.save(user);
        Assert.assertEquals(user, savedUser);
    }

    @Test
    public void update() {
        User savedUser = userRepository.save(new User("user1", "Pupkin", "Vasya", "Vasilievich", "vasya@tut.ru", "123456", "dvornic"));
        savedUser.setLastname("Testov");
        User updatedUser = userRepository.save(savedUser);
        Assert.assertEquals(updatedUser, savedUser);
    }

    @Test
    public void delete() {
        User savedUser = userRepository.save(new User("user1", "Pupkin", "Vasya", "Vasilievich", "vasya@tut.ru", "123456", "dvornic"));
        userRepository.delete(savedUser.getId());
        List<User> returnedUsers = userRepository.findAll();
        Assert.assertEquals(Collections.emptyList(), returnedUsers);

    }
}
