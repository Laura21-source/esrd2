package ru.gbuac.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.SafeHtml;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User extends NamedEntity {
    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "patronym")
    private String patronym;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "position")
    private String position;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles;

    public User(@NotBlank @SafeHtml String name, String lastname, String firstname, String patronym, String email,
                String phone, String position) {
        super(name);
        this.lastname = lastname;
        this.firstname = firstname;
        this.patronym = patronym;
        this.email = email;
        this.phone = phone;
        this.position = position;
    }
}
