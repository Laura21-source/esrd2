package ru.gbuac.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_ELECTRO_ENERGY,
    ROLE_FUEL_ENERGY;


    @Override
    public String getAuthority() {
        return name();
    }
}
