package ru.gbuac.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.gbuac.model.Field;
import ru.gbuac.model.FieldType;
import ru.gbuac.model.ValuedField;

import java.util.ArrayList;
import java.util.List;

public class FieldTo extends BaseTo {

    @JsonInclude
    String name;

    List<FieldTo> childFields;

    @JsonInclude
    Integer fieldId;

    @JsonInclude
    FieldType fieldType;

    @JsonInclude
    Integer positionInGroup;

    @JsonInclude
    Integer maxCount;

    @JsonInclude
    Integer length;

    @JsonInclude
    Integer catalogId;

    @JsonInclude
    String value;

    public FieldTo(Field field) {
        this.name = field.getName();
        this.fieldId = field.getId();
        this.fieldType = field.getFieldType();
        this.positionInGroup = field.getPositionInGroup();
        this.maxCount = field.getMaxCount();
        this.length = field.getLength();
        this.catalogId = field.getCatalog_id();
        this.childFields = new ArrayList<>();
        for (Field childField : field.getChildField()) {
            this.childFields.add(FieldTo(childField));
        }
    }

    public FieldTo(ValuedField field) {
        this.name = field.getField().getName();
        this.fieldId = field.getField().getId();
        this.fieldType = field.getField().getFieldType();
        this.positionInGroup = field.getField().getPositionInGroup();
        this.maxCount = field.getField().getMaxCount();
        this.length = field.getField().getLength();
        this.catalogId = field.getField().getCatalog_id();
        this.childFields = new ArrayList<>();
        for (ValuedField childField : field.getChildValuedField()) {
            this.childFields.add(FieldTo(childField));
        }
    }

    public FieldTo(String name, List<FieldTo> childFields, Integer fieldId, FieldType fieldType, Integer positionInGroup, Integer maxCount, Integer length, Integer catalogId) {
        this.name = name;
        this.childFields = childFields;
        this.fieldId = fieldId;
        this.fieldType = fieldType;
        this.positionInGroup = positionInGroup;
        this.maxCount = maxCount;
        this.length = length;
        this.catalogId = catalogId;
    }

    public FieldTo FieldTo(Field field) {
        FieldTo fieldTo = new FieldTo(field.getName(), new ArrayList<>(), field.getId(), field.getFieldType(),
                field.getPositionInGroup(), field.getMaxCount(), field.getLength(),
                field.getCatalog_id());

        for (Field childField : field.getChildField()) {
            fieldTo.childFields.add(FieldTo(childField));
        }
        return fieldTo;
    }

    public FieldTo FieldTo(ValuedField field) {
        FieldTo fieldTo = new FieldTo(field.getField().getName(), new ArrayList<>(), field.getId(), field.getField().getFieldType(),
                field.getField().getPositionInGroup(), field.getField().getMaxCount(), field.getField().getLength(),
                field.getField().getCatalog_id());

        for (ValuedField childField : field.getChildValuedField()) {
            fieldTo.childFields.add(FieldTo(childField));
        }
        return fieldTo;
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

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldTo fieldTo = (FieldTo) o;

        if (name != null ? !name.equals(fieldTo.name) : fieldTo.name != null) return false;
        if (childFields != null ? !childFields.equals(fieldTo.childFields) : fieldTo.childFields != null)
            return false;
        if (fieldId != null ? !fieldId.equals(fieldTo.fieldId) : fieldTo.fieldId != null) return false;
        if (fieldType != fieldTo.fieldType) return false;
        if (positionInGroup != null ? !positionInGroup.equals(fieldTo.positionInGroup) : fieldTo.positionInGroup != null)
            return false;
        if (maxCount != null ? !maxCount.equals(fieldTo.maxCount) : fieldTo.maxCount != null) return false;
        if (length != null ? !length.equals(fieldTo.length) : fieldTo.length != null) return false;
        if (catalogId != null ? !catalogId.equals(fieldTo.catalogId) : fieldTo.catalogId != null) return false;
        return value != null ? value.equals(fieldTo.value) : fieldTo.value == null;
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
        result = 31 * result + (catalogId != null ? catalogId.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FieldTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", child_fields=" + childFields +
                ", field_id=" + fieldId +
                ", fieldtype=" + fieldType +
                ", position_in_group=" + positionInGroup +
                ", maxCount=" + maxCount +
                ", length=" + length +
                ", catalog_id=" + catalogId +
                ", value='" + value + '\'' +
                '}';
    }
}
