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
    @Query("DELETE FROM Role r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT r FROM Role r JOIN r.users u JOIN r.childRole c WHERE u.name IN :userName")
    List<Role> getRolesByUsername(@Param("userName") String userName);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Role r JOIN r.users u WHERE u.name=:userName AND r.name=:role")
    boolean isUsernameHasRole(@Param("userName") String userName, @Param("role") String role);

}
