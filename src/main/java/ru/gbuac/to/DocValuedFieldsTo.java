package ru.gbuac.to;

import ru.gbuac.model.Doc;
import ru.gbuac.model.ValuedField;

public class DocValuedFieldsTo extends BaseTo {
    Doc doc;

    ValuedField valuedField;

    Integer position;

    public DocValuedFieldsTo(Integer id, Doc doc, ValuedField valuedField, Integer position) {
        super(id);
        this.doc = doc;
        this.valuedField = valuedField;
        this.position = position;
    }

    public Doc getDoc() {
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public ValuedField getValuedField() {
        return valuedField;
    }

    public void setValuedField(ValuedField valuedField) {
        this.valuedField = valuedField;
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

        DocValuedFieldsTo that = (DocValuedFieldsTo) o;

        if (doc != null ? !doc.equals(that.doc) : that.doc != null) return false;
        if (valuedField != null ? !valuedField.equals(that.valuedField) : that.valuedField != null) return false;
        return position != null ? position.equals(that.position) : that.position == null;
    }

    @Override
    public int hashCode() {
        int result = doc != null ? doc.hashCode() : 0;
        result = 31 * result + (valuedField != null ? valuedField.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DocValuedFieldsTo{" +
                "id=" + id +
                ", doc=" + doc +
                ", valuedField=" + valuedField +
                ", position=" + position +
                '}';
    }
}
