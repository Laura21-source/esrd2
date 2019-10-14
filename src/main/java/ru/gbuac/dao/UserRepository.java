package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.User;
import ru.gbuac.to.UserTo;

import java.util.List;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT u FROM User u WHERE u.name=:name")
    User getByName(@Param("name") String name);

    @Query("SELECT new ru.gbuac.to.UserTo(u.id, CONCAT(u.lastname, ' ', u.firstname, ' ', u.patronym), u.phone, u.position) " +
            "FROM User u WHERE u.id IN (4000, 4001, 4002, 4195, 4336, 4188, 4330, 4334, 4331, 4337, 4332, 4335, 4096, 4192, 4193, 4194, " +
            "4189, 4187, 4196, 4190, 4289, 4291, 4294, 4293, 4084, 4373, 4329) ORDER BY u.lastname ASC")
    List<UserTo> getAll();
}
