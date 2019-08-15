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
    private List<ValuedField> childValuedField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalogelem_id", nullable = true)
    private CatalogElem catalogElem;

    @Column(name = "value_int")
    private Integer valueInt;

    @Column(name = "value_str")
    private String valueStr;

    @Column(name = "value_date")
    private LocalDateTime valueDate;

    @Column(name = "value_time")
    private LocalDateTime valueTime;

    @Column(name = "value_datetime")
    private LocalDateTime valueDateTime;
}
