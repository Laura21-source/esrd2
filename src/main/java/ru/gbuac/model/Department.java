package ru.gbuac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "department")
public class Department extends NamedEntity {

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="parent_department_id")
    private Department parentDepartmentId;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department", cascade = CascadeType.ALL)
    private List<User> users;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Department> childDepartments;
}
