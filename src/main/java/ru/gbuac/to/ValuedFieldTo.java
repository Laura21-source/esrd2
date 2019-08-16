package ru.gbuac.to;

import ru.gbuac.model.ValuedField;

import java.util.List;

public class ValuedFieldTo extends BaseTo {

    private Integer fieldId;

    private String value;

    private List<ValuedField> childFields;

    public ValuedFieldTo(Integer id, Integer fieldId, String value, List<ValuedField> childFields) {
        super(id);
        this.fieldId = fieldId;
        this.value = value;
        this.childFields = childFields;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ValuedField> getChildFields() {
        return childFields;
    }

    public void setChildFields(List<ValuedField> childFields) {
        this.childFields = childFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValuedFieldTo that = (ValuedFieldTo) o;

        if (fieldId != null ? !fieldId.equals(that.fieldId) : that.fieldId != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        return childFields != null ? childFields.equals(that.childFields) : that.childFields == null;
    }

    @Override
    public int hashCode() {
        int result = fieldId != null ? fieldId.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (childFields != null ? childFields.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ValuedFieldTo{" +
                "id=" + id +
                ", field_id=" + fieldId +
                ", value='" + value + '\'' +
                ", child_fields=" + childFields +
                '}';
    }
}
