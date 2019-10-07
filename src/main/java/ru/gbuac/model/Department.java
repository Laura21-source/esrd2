package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Department extends NamedEntity {

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Department> childDepartment;


}
