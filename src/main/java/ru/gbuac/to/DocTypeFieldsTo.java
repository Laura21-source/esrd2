package ru.gbuac.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.gbuac.model.BaseEntity;
import ru.gbuac.model.DocType;
import ru.gbuac.model.Role;

public class DocTypeFieldsTo {
    @JsonIgnore
    protected Integer id;

    @JsonIgnore
    DocType docType;

    FieldTo field;

    Integer position;

    private Role role;

    public DocTypeFieldsTo(Integer id, DocType docType, FieldTo field, Integer position, Role role) {
        this.id = id;
        this.docType = docType;
        this.field = field;
        this.position = position;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public DocType getDocType() {
        return docType;
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

        DocTypeFieldsTo that = (DocTypeFieldsTo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (docType != null ? !docType.equals(that.docType) : that.docType != null) return false;
        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (docType != null ? docType.hashCode() : 0);
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DocTypeFieldsTo{" +
                " id=" + id +
                ", docType=" + docType +
                ", field=" + field +
                ", position=" + position +
                ", role=" + role +
                '}';
    }
}
