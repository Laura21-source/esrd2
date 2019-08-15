package ru.gbuac.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.gbuac.model.Field;
import ru.gbuac.model.FieldType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FieldTo extends BaseTo {

    @JsonInclude
    String name;

    List<FieldTo> child_fields;

    @JsonInclude
    Integer field_id;

    @JsonInclude
    FieldType fieldtype;

    @JsonInclude
    Integer position_in_group;

    @JsonInclude
    Integer maxCount;

    @JsonInclude
    Integer length;

    @JsonInclude
    Integer catalog_id;

    @JsonInclude
    String value;

    public FieldTo(Field field) {
        this.name = field.getName();
        this.field_id = field.getId();
        this.fieldtype = field.getFieldType();
        this.position_in_group = field.getPositionInGroup();
        this.maxCount = field.getMaxCount();
        this.length = field.getLength();
        this.catalog_id = field.getCatalog_id();
        this.child_fields = new ArrayList<>();
        for (Field childField : field.getChildField()) {
            this.child_fields.add(FieldTo(childField));
        }
    }

    public FieldTo(String name, List<FieldTo> child_fields, Integer field_id, FieldType fieldtype, Integer position_in_group, Integer maxCount, Integer length, Integer catalog_id) {
        this.name = name;
        this.child_fields = child_fields;
        this.field_id = field_id;
        this.fieldtype = fieldtype;
        this.position_in_group = position_in_group;
        this.maxCount = maxCount;
        this.length = length;
        this.catalog_id = catalog_id;
    }

    public FieldTo FieldTo(Field field) {
        FieldTo fieldTo = new FieldTo(field.getName(), new ArrayList<>(), field.getId(), field.getFieldType(),
                field.getPositionInGroup(), field.getMaxCount(), field.getLength(),
                field.getCatalog_id());

        for (Field childField : field.getChildField()) {
            fieldTo.child_fields.add(FieldTo(childField));
        }
        return fieldTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FieldTo> getChild_fields() {
        return child_fields;
    }

    public void setChild_fields(List<FieldTo> child_fields) {
        this.child_fields = child_fields;
    }

    public Integer getField_id() {
        return field_id;
    }

    public void setField_id(Integer field_id) {
        this.field_id = field_id;
    }

    public FieldType getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(FieldType fieldtype) {
        this.fieldtype = fieldtype;
    }

    public Integer getPosition_in_group() {
        return position_in_group;
    }

    public void setPosition_in_group(Integer position_in_group) {
        this.position_in_group = position_in_group;
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

    public Integer getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(Integer catalog_id) {
        this.catalog_id = catalog_id;
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
        if (child_fields != null ? !child_fields.equals(fieldTo.child_fields) : fieldTo.child_fields != null)
            return false;
        if (field_id != null ? !field_id.equals(fieldTo.field_id) : fieldTo.field_id != null) return false;
        if (fieldtype != fieldTo.fieldtype) return false;
        if (position_in_group != null ? !position_in_group.equals(fieldTo.position_in_group) : fieldTo.position_in_group != null)
            return false;
        if (maxCount != null ? !maxCount.equals(fieldTo.maxCount) : fieldTo.maxCount != null) return false;
        if (length != null ? !length.equals(fieldTo.length) : fieldTo.length != null) return false;
        if (catalog_id != null ? !catalog_id.equals(fieldTo.catalog_id) : fieldTo.catalog_id != null) return false;
        return value != null ? value.equals(fieldTo.value) : fieldTo.value == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (child_fields != null ? child_fields.hashCode() : 0);
        result = 31 * result + (field_id != null ? field_id.hashCode() : 0);
        result = 31 * result + (fieldtype != null ? fieldtype.hashCode() : 0);
        result = 31 * result + (position_in_group != null ? position_in_group.hashCode() : 0);
        result = 31 * result + (maxCount != null ? maxCount.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (catalog_id != null ? catalog_id.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FieldTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", child_fields=" + child_fields +
                ", field_id=" + field_id +
                ", fieldtype=" + fieldtype +
                ", position_in_group=" + position_in_group +
                ", maxCount=" + maxCount +
                ", length=" + length +
                ", catalog_id=" + catalog_id +
                ", value='" + value + '\'' +
                '}';
    }
}
