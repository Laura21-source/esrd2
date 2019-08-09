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
public class DocTypeFields {
    @Id
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doctype", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    DocType docType;

    @Id
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_field", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Field field;

    @NotNull
    Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_groupedfield", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    GroupedField groupedField;

    @Column(name = "position_in_group")
    Integer positionInGroup;

    @NotNull
    @Column(name = "max_count")
    Integer maxCount;
}
