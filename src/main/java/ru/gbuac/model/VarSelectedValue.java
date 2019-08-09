package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class VarSelectedValue extends BaseEntity {
    @Column(name = "id_varelem")
    Integer idVarElem;

    @OneToOne
    VarSelectedValue idChildSelValue;
}
