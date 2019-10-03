package ru.gbuac.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "organization")
public class Organization extends BaseEntity {

    @SafeHtml
    @Column(name = "short_name")
    private String shortName;

    @SafeHtml
    @Column(name = "full_name")
    public String fullName;

    @SafeHtml
    @Column(name = "ogrn")
    public String ogrn;

    @SafeHtml
    @Column(name = "inn")
    public String inn;

    @SafeHtml
    @Column(name = "kpp")
    private String kpp;

    @SafeHtml
    @Column(name = "address")
    private String address;

    @SafeHtml
    @Column(name = "fio_manager")
    private String fioManager;

    @SafeHtml
    @Column(name = "position_manager")
    private String positionManager;
}
