package ru.gbuac.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import ru.gbuac.model.Role;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepository(@Qualifier("dataSource") DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GrantedAuthority> getAuthoritiesByUsername(String username) {
        String query = "SELECT role FROM user_roles a WHERE a.userldap=?";

        List<GrantedAuthority> authorities =
                jdbcTemplate.query(query, (rs, rowNum) -> Role.valueOf(rs.getString(1)), username);
        return authorities;
    }

    public List<Role> getRolesByUsername(String username) {
        String query = "SELECT role FROM user_roles a WHERE a.userldap=?";

        List<Role> roles =
                jdbcTemplate.query(query, (rs, rowNum) -> Role.valueOf(rs.getString(1)), username);
        return roles;
    }

}
