package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.Role;
import java.util.List;

@Transactional(readOnly = true)
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT FROM Role r WHERE r.id=:id")
    List<GrantedAuthority> getAuthoritiesByUsername(@Param("userName") String username);
​
    @Query("SELECT r FROM Role r WHERE r id=:id")
    List<Role> getRolesByUsername(@Param("userName") String username);


    @Query("SELECT new ru.gbuac.model.User.i) FROM User a " +
            "WHERE a.id = " +
            "(SELECT min(a1.id) FROM Answer a1, Question q " +
            " WHERE a1.question.id = q.id AND q.id=:questionId AND a1.id > :id)")
    AnswerTo getOneMoreThanId(@Param("id") int id, @Param("UserId") int questionId);

}
