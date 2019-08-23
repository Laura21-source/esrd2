package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository<Role> extends JpaRepository {
    default void getRolesByUsername() {

    }

    default void getAuthorityByUserName() {

    }
}
