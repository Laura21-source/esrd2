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
@IdClass(DocTypeFields.class)
@EqualsAndHashCode
@ToString
@Table(name = "doctype_fields")
public class DocTypeFields implements Serializable {
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
