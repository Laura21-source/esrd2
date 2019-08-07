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
public class DocTypeFields extends BaseEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doctype", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    DocType docType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_field", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Field field;

    @NotNull
    Integer position;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_groupedfield", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    GroupedField groupedField;

    @NotNull
    @Column(name = "position_group")
    Integer positionGroup;

    @NotNull
    @Column(name = "max_count")
    Integer maxCount;
}
