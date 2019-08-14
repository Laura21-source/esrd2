package ru.gbuac.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doc_valuedfields")
public class DocValuedFields extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doc_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Doc doc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "valuedfield_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    ValuedField valuedField;

    @NotNull
    @Column(name = "position")
    Integer position;
}
