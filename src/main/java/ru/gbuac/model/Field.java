package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "field")
public class Field extends NamedEntity {
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Field> childField;

    @Enumerated(EnumType.STRING)
    @Column(name = "fieldtype")
    FieldType fieldType;

    @Column(name = "position_in_group")
    Integer positionInGroup;

    @Column(name = "max_count")
    Integer maxCount;

    Integer length;

    Integer catalog_id;
}
