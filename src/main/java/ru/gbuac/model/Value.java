package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "value")
public class Value extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalogelem_id", nullable = true)
    CatalogElem catalogElem;

    @Column(name = "value_int")
    Integer valueInt;

    @Column(name = "value_str")
    String valueStr;

    @Column(name = "value_date")
    LocalDateTime valueDate;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "values")
    private List<Field> fields;
}
