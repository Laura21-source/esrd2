package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Field extends NamedEntity {

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "id_fieldtype")
    FieldType fieldType;

    int length;
}
