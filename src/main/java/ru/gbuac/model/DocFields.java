package ru.gbuac.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(DocFields.class)
@Table(name = "docfields")
@EqualsAndHashCode
@ToString
public class DocFields implements Serializable {
    @Id
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doc", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Doc doc;

    @Id
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_varselectedvalues", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    VarSelectedValue varSelectedValue;

    @Column(name = "value_int")
    Integer valueInt;

    @Column(name = "value_str")
    String valueStr;

    @Column(name = "value_date")
    String valueDate;
}
