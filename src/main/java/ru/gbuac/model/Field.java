package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Field extends NamedEntity {

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "id_fieldtype")
    FieldType fieldType;

    @NotNull
    int length;
}
