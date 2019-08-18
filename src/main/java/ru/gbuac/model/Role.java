package ru.gbuac.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_POVESTKA,
    ROLE_POVESTKA_QUESTIONS,
    ROLE_SECRETARY;

    @Override
    public String getAuthority() {
        return name();
    }
}
