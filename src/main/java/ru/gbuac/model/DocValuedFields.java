package ru.gbuac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Doc doc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "valuedfield_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ValuedField valuedField;

    @NotNull
    private Integer position;
}
