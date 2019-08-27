package ru.gbuac.repository;

import org.junit.Test;
import ru.gbuac.model.User;

public class UserRepositoryTest extends AbsractRepositoryTest {

    @Test
    public  void create() {
        User user = new User("Vasya", "Pupkin", "Vasilievich", "vasya@tut.ru", "123456", "dvornic");
    }


    public void read() {

    }

    public void update() {

    }

    public void delete() {

    }

    public void getAll() {

    }

}
