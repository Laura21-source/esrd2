package ru.gbuac.to;

import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CatalogElemTo extends BaseTo {
    private Integer idParentCatalogElem;

    private String valueInt;

    private String valueStr;

    private Integer catalogId;

    public CatalogElemTo(Integer id, Integer idParentCatalogElem, String valueInt, String valueStr, Integer catalogId) {
        super(id);
        this.idParentCatalogElem = idParentCatalogElem;
        this.valueInt = valueInt;
        this.valueStr = valueStr;
        this.catalogId = catalogId;
    }
}
