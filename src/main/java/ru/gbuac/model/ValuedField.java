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
@Table(name = "valuedfield")
public class ValuedField extends BaseEntity{
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<ValuedField> childValuedfield;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    Field field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalogelem_id", nullable = true)
    CatalogElem catalogElem;

    @Column(name = "value_int")
    Integer valueInt;

    @Column(name = "value_str")
    String valueStr;

    @Column(name = "value_date")
    LocalDateTime valueDate;
}
