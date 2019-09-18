package ru.gbuac.to;

import lombok.*;
import ru.gbuac.model.FieldType;
import ru.gbuac.model.Role;
import ru.gbuac.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class FieldTo extends BaseTo {

    private String name;

    private List<FieldTo> childFields;

    private Integer fieldId;

    private FieldType fieldType;

    private Integer positionInGroup;

    private Integer maxCount;

    private Integer length;

    private Integer parentCatalogId;

    private Integer catalogId;

    private Boolean enabled;

    private Boolean required;

    private Role role;

    private String tag;

    private Integer valueInt;

    private String valueStr;

    private LocalDateTime valueDate;

    public FieldTo(Integer id, String name, List<FieldTo> childFields, Integer fieldId, FieldType fieldType,
                   Integer positionInGroup, Integer maxCount, Integer length,
                   Integer parentCatalogId, Integer catalogId, Boolean enabled,
                   Boolean required, Role role, String tag) {
        super(id);
        this.name = name;
        this.childFields = childFields;
        this.fieldId = fieldId;
        this.fieldType = fieldType;
        this.positionInGroup = positionInGroup;
        this.maxCount = maxCount;
        this.length = length;
        this.parentCatalogId = parentCatalogId;
        this.catalogId = catalogId;
        this.enabled = enabled;
        this.required = required;
        this.role = role;
        this.tag = tag;
    }
}
