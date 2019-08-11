package ru.gbuac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doctype_fields")
public class DocTypeFields extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doctype", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    DocType docType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_field", nullable = true)
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

    @Column(name = "max_count")
    Integer maxCount;

    @Enumerated(EnumType.STRING)
    private Role role;
}
