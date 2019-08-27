package ru.gbuac.to;

import ru.gbuac.model.FieldType;
import ru.gbuac.model.Role;
import ru.gbuac.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;

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

    public FieldTo() {
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FieldTo> getChildFields() {
        return childFields;
    }

    public void setChildFields(List<FieldTo> childFields) {
        this.childFields = childFields;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getPositionInGroup() {
        return positionInGroup;
    }

    public void setPositionInGroup(Integer positionInGroup) {
        this.positionInGroup = positionInGroup;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getParentCatalogId() {
        return parentCatalogId;
    }

    public void setParentCatalogId(Integer parentCatalogId) {
        this.parentCatalogId = parentCatalogId;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getValueInt() {
        return valueInt;
    }

    public void setValueInt(Integer valueInt) {
        this.valueInt = valueInt;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public LocalDateTime getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDateTime valueDate) {
        this.valueDate = valueDate;
    }

    public String getValueByFieldType() {
        switch (fieldType) {
            case TEXT:
            case TEXTAREA:
                return getValueStr();
            case NUMBER:
                return getValueInt().toString();
            case DATE:
                return getValueDate() != null ? DateTimeUtil.toStringPrint(getValueDate().toLocalDate()) : "";
            case TIME:
                return getValueDate() != null ?DateTimeUtil.toString(getValueDate().toLocalTime()) : "";
            case DATETIME:
                return getValueDate() != null ?DateTimeUtil.toStringPrint(getValueDate()) : "";
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldTo fieldTo = (FieldTo) o;

        if (name != null ? !name.equals(fieldTo.name) : fieldTo.name != null) return false;
        if (childFields != null ? !childFields.equals(fieldTo.childFields) : fieldTo.childFields != null) return false;
        if (fieldId != null ? !fieldId.equals(fieldTo.fieldId) : fieldTo.fieldId != null) return false;
        if (fieldType != fieldTo.fieldType) return false;
        if (positionInGroup != null ? !positionInGroup.equals(fieldTo.positionInGroup) : fieldTo.positionInGroup != null)
            return false;
        if (maxCount != null ? !maxCount.equals(fieldTo.maxCount) : fieldTo.maxCount != null) return false;
        if (length != null ? !length.equals(fieldTo.length) : fieldTo.length != null) return false;
        if (parentCatalogId != null ? !parentCatalogId.equals(fieldTo.parentCatalogId) : fieldTo.parentCatalogId != null)
            return false;
        if (catalogId != null ? !catalogId.equals(fieldTo.catalogId) : fieldTo.catalogId != null) return false;
        if (enabled != null ? !enabled.equals(fieldTo.enabled) : fieldTo.enabled != null) return false;
        if (required != null ? !required.equals(fieldTo.required) : fieldTo.required != null) return false;
        if (role != fieldTo.role) return false;
        if (tag != null ? !tag.equals(fieldTo.tag) : fieldTo.tag != null) return false;
        if (valueInt != null ? !valueInt.equals(fieldTo.valueInt) : fieldTo.valueInt != null) return false;
        if (valueStr != null ? !valueStr.equals(fieldTo.valueStr) : fieldTo.valueStr != null) return false;
        return valueDate != null ? valueDate.equals(fieldTo.valueDate) : fieldTo.valueDate == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (childFields != null ? childFields.hashCode() : 0);
        result = 31 * result + (fieldId != null ? fieldId.hashCode() : 0);
        result = 31 * result + (fieldType != null ? fieldType.hashCode() : 0);
        result = 31 * result + (positionInGroup != null ? positionInGroup.hashCode() : 0);
        result = 31 * result + (maxCount != null ? maxCount.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (parentCatalogId != null ? parentCatalogId.hashCode() : 0);
        result = 31 * result + (catalogId != null ? catalogId.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (required != null ? required.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (valueInt != null ? valueInt.hashCode() : 0);
        result = 31 * result + (valueStr != null ? valueStr.hashCode() : 0);
        result = 31 * result + (valueDate != null ? valueDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FieldTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", childFields=" + childFields +
                ", fieldId=" + fieldId +
                ", fieldType=" + fieldType +
                ", positionInGroup=" + positionInGroup +
                ", maxCount=" + maxCount +
                ", length=" + length +
                ", parentCatalogId=" + parentCatalogId +
                ", catalogId=" + catalogId +
                ", enabled=" + enabled +
                ", required=" + required +
                ", role=" + role +
                ", tag='" + tag + '\'' +
                ", valueInt=" + valueInt +
                ", valueStr='" + valueStr + '\'' +
                ", valueDate=" + valueDate +
                '}';
    }
}
