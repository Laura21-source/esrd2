package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "var")
public class Var extends NamedEntity {
    @Column(name = "id_parent_var")
    Integer idParentVar;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "id_vartype")
    VarType varType;
}
