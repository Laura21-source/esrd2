package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Field extends NamedEntity {

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "id_fieldtype")
    FieldType fieldType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_var", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Var var;

    @NotNull
    int length;
}
