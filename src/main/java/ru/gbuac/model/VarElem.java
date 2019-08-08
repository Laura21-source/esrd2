package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class VarElem extends NamedEntity {
    @Column(name = "id_parent_varelem")
    Integer idParentVarElem;

    @Column(name = "value_int")
    String valueInt;

    @Column(name = "value_str")
    String valueStr;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_var", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Var var;
}
