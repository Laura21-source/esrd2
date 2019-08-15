package ru.gbuac.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.gbuac.model.DocType;
import ru.gbuac.model.Role;

public class DocFieldsTo {
    @JsonIgnore
    protected Integer id;

    FieldTo field;

    Integer position;

    private Role role;

    public DocFieldsTo(Integer id, FieldTo field, Integer position, Role role) {
        this.id = id;
        this.field = field;
        this.position = position;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public FieldTo getField() {
        return field;
    }

    public Integer getPosition() {
        return position;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocFieldsTo that = (DocFieldsTo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DocTypeFieldsTo{" +
                " id=" + id +
                ", field=" + field +
                ", position=" + position +
                ", role=" + role +
                '}';
    }
}
