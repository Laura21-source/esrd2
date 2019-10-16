package ru.gbuac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "department")
public class Department extends NamedEntity {

    @JsonIgnore
    @Column(name="top_level")
    private boolean topLevel;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chief_user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User chiefUser;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "department", cascade = CascadeType.ALL)
    private List<User> users;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Department> childDepartments;
}
