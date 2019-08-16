package ru.gbuac.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.gbuac.model.Field;
import ru.gbuac.model.FieldType;
import ru.gbuac.model.ValuedField;
import ru.gbuac.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FieldTo extends BaseTo {

    private String name;

    private List<FieldTo> childFields;

    private Integer fieldId;

    private FieldType fieldType;

    private Integer positionInGroup;

    private Integer maxCount;

    private Integer length;

    private Integer catalogId;

    private Integer valueInt;

    private String valueStr;

    private LocalDateTime valueDate;

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

    public FieldTo(ValuedField valuedField) {
        this.id = valuedField.getId();
        this.name = valuedField.getField().getName();
        this.fieldId = valuedField.getField().getId();
        this.fieldType = valuedField.getField().getFieldType();
        this.positionInGroup = valuedField.getField().getPositionInGroup();
        this.maxCount = valuedField.getField().getMaxCount();
        this.length = valuedField.getField().getLength();
        this.catalogId = valuedField.getField().getCatalog_id();
        this.childFields = new ArrayList<>();
        switch (this.fieldType.ordinal()) {
            case 0:
            case 1:
                this.valueStr = valuedField.getValueStr();
                break;
            case 2:
                this.valueInt = valuedField.getValueInt();
                break;
            case 3:
                this.valueDate = valuedField.getValueDate();
                break;
            case 4:
                this.valueDate = valuedField.getValueTime();
                break;
            case 5:
                this.valueDate = valuedField.getValueDateTime();
                break;
            case 6:
                this.valueInt = valuedField.getCatalogElem().getId();
        }

        for (ValuedField childField : valuedField.getChildValuedField()) {
            this.childFields.add(FieldTo(childField));
        }
    }

    public FieldTo(Integer id, String name, List<FieldTo> childFields, Integer fieldId, FieldType fieldType, Integer positionInGroup, Integer maxCount, Integer length, Integer catalogId) {
        super(id);
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
        FieldTo fieldTo = new FieldTo(field);

        for (Field childField : field.getChildField()) {
            fieldTo.childFields.add(FieldTo(childField));
        }
        return fieldTo;
    }

    public FieldTo FieldTo(ValuedField valuedField) {
        FieldTo fieldTo = new FieldTo(valuedField);

        for (ValuedField childField : valuedField.getChildValuedField()) {
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
        if (catalogId != null ? !catalogId.equals(fieldTo.catalogId) : fieldTo.catalogId != null) return false;
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
        result = 31 * result + (catalogId != null ? catalogId.hashCode() : 0);
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
                ", catalogId=" + catalogId +
                ", valueInt=" + valueInt +
                ", valueStr='" + valueStr + '\'' +
                ", valueDate=" + valueDate +
                '}';
    }
}
