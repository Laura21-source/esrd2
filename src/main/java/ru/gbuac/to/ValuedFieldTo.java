package ru.gbuac.to;

import ru.gbuac.model.CatalogElem;
import ru.gbuac.model.Field;
import ru.gbuac.model.FieldType;
import ru.gbuac.model.ValuedField;

import java.time.LocalDateTime;
import java.util.List;

public class ValuedFieldTo extends BaseTo {

    Integer field_id;

    String value;

    List<ValuedField> child_fields;

    public ValuedFieldTo(Integer id, Integer field_id, String value, List<ValuedField> child_fields) {
        super(id);
        this.field_id = field_id;
        this.value = value;
        this.child_fields = child_fields;
    }

    public Integer getField_id() {
        return field_id;
    }

    public void setField_id(Integer field_id) {
        this.field_id = field_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ValuedField> getChild_fields() {
        return child_fields;
    }

    public void setChild_fields(List<ValuedField> child_fields) {
        this.child_fields = child_fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValuedFieldTo that = (ValuedFieldTo) o;

        if (field_id != null ? !field_id.equals(that.field_id) : that.field_id != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        return child_fields != null ? child_fields.equals(that.child_fields) : that.child_fields == null;
    }

    @Override
    public int hashCode() {
        int result = field_id != null ? field_id.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (child_fields != null ? child_fields.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ValuedFieldTo{" +
                "id=" + id +
                ", field_id=" + field_id +
                ", value='" + value + '\'' +
                ", child_fields=" + child_fields +
                '}';
    }
}
