package ru.gbuac.util;

import ru.gbuac.model.Field;
import ru.gbuac.model.FieldType;
import ru.gbuac.model.ValuedField;
import ru.gbuac.to.FieldTo;

import java.util.ArrayList;
import java.util.List;

public class FieldUtil {

    public static FieldTo asTo(Field field) {
        List<FieldTo> childFields = new ArrayList<>();
        for (Field childField : field.getChildField()) {
            childFields.add(asTo(childField));
        }
        return new FieldTo(null, field.getName(), childFields, field.getId(), field.getFieldType(),
                field.getPositionInGroup(), field.getMaxCount(), field.getLength(), field.getCatalog_id(), field.getTag());
    }

    public static FieldTo asTo(ValuedField valuedField) {
        List<FieldTo> childFields = new ArrayList<>();
        for (ValuedField childField : valuedField.getChildValuedField()) {
            childFields.add(asTo(childField));
        }
        FieldType fieldType = valuedField.getField().getFieldType();

        FieldTo fieldTo = new FieldTo(valuedField.getId(), valuedField.getField().getName(), childFields,
                valuedField.getField().getId(), fieldType, valuedField.getField().getPositionInGroup(),
                valuedField.getField().getMaxCount(), valuedField.getField().getLength(),
                valuedField.getField().getCatalog_id(), valuedField.getField().getTag());

        switch (fieldType) {
            case TEXT:
            case TEXT_MULTI_LINE:
                fieldTo.setValueStr(valuedField.getValueStr());
                break;
            case NUMBER:
                fieldTo.setValueInt(valuedField.getValueInt());
                break;
            case DATE:
                fieldTo.setValueDate(valuedField.getValueDate());
                break;
            case TIME:
                fieldTo.setValueDate(valuedField.getValueTime());
                break;
            case DATE_TIME:
                fieldTo.setValueDate(valuedField.getValueDateTime());
                break;
            case CATALOG:
                fieldTo.setValueInt(valuedField.getCatalogElem().getId());
        }
        return fieldTo;
    }
}
