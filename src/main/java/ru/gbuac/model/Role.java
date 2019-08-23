package ru.gbuac.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
