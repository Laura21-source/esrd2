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
public class DocFields extends BaseEntity{
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doc", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Doc doc;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_field", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Field field;

    @NotNull
    @Column(name = "position_fact")
    Integer positionFact;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_groupedfield", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    GroupedField groupedField;

    @Column(name = "position_in_group_fact")
    Integer positionInGroupFact;

    @Column(name = "value_int")
    Integer valueInt;

    @Column(name = "value_str")
    String valueStr;

    @Column(name = "value_date")
    String valueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_var", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Var var;


}
