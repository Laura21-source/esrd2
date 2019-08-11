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
@Table(name = "catalogelem")
public class CatalogElem extends BaseEntity {
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="parent_catalogelem_id")
    CatalogElem idParentCatalogElem;

    @Column(name = "value_int")
    String valueInt;

    @Column(name = "value_str")
    String valueStr;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Catalog catalog;
}
