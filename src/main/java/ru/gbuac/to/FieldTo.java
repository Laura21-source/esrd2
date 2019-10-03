package ru.gbuac.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.gbuac.model.FieldType;
import ru.gbuac.model.Role;
import ru.gbuac.util.DateTimeUtil;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
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
                   Boolean required, String tag) {
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
        this.tag = tag;
    }
    public String getValueByFieldType() {
        switch (fieldType) {
            case TEXT:
            case TEXTAREA:
                return getValueStr() != null ? getValueStr() : "";
            case NUMBER:
                return getValueInt() != null ? getValueInt().toString() : "";
            case DATE:
                return getValueDate() != null ? DateTimeUtil.toStringPrint(getValueDate().toLocalDate()) : "";
            case TIME:
                return getValueDate() != null ?DateTimeUtil.toString(getValueDate().toLocalTime()) : "";
            case DATETIME:
                return getValueDate() != null ?DateTimeUtil.toStringPrint(getValueDate()) : "";
            case CATALOG_USERS:
            case CATALOG_ORGANIZATIONS:
            case CATALOG_REGNUMBERS:
                return getValueInt() != null ? getValueInt().toString() : "";
        }
        return "";
    }
}
