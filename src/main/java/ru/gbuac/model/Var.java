package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Var extends NamedEntity {
    @Column(name = "id_parent_var")
    Integer idParentVar;

    @NotNull
    Integer varType;
}
