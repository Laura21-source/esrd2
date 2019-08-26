package ru.gbuac.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.gbuac.model.Role;

public class DocFieldsTo {
    @JsonIgnore
    private Integer id;

    private FieldTo field;

    private Integer position;

    public DocFieldsTo() {
    }

    public DocFieldsTo(Integer id, FieldTo field, Integer position) {
        this.id = id;
        this.field = field;
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FieldTo getField() {
        return field;
    }

    public void setField(FieldTo field) {
        this.field = field;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocFieldsTo that = (DocFieldsTo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        return position != null ? position.equals(that.position) : that.position == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DocFieldsTo{" +
                "id=" + id +
                ", field=" + field +
                ", position=" + position +
                '}';
    }
}
