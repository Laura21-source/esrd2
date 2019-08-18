package ru.gbuac.model;

import org.hibernate.annotations.BatchSize;
import javax.persistence.*;
import java.util.*;

@Entity
public class User extends NamedEntity {
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "userldap"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;


}
