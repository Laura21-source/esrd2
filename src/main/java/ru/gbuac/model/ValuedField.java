package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "valuedfield")
public class ValuedField extends BaseEntity{

    @Column(name = "value_int")
    Integer valueInt;

    @Column(name = "value_str")
    String valueStr;

    @Column(name = "value_date")
    String valueDate;
}
