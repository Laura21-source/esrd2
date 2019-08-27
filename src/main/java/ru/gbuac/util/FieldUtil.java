package ru.gbuac.util;

import ru.gbuac.model.Field;
import ru.gbuac.model.FieldType;
import ru.gbuac.model.Role;
import ru.gbuac.model.ValuedField;
import ru.gbuac.to.FieldTo;

import java.util.ArrayList;
import java.util.List;

public class FieldUtil {

    public FieldUtil() {
    }

    public static FieldTo asTo(Field field, List<Role> curUserRoles) {
        List<FieldTo> childFields = new ArrayList<>();
        for (Field childField : field.getChildField()) {
            childField.setRole(field.getRole());
            childFields.add(asTo(childField, curUserRoles));
        }
        Boolean enabled = curUserRoles.contains(field.getRole());

        return new FieldTo(null, field.getName(), childFields, field.getId(), field.getFieldType(),
                field.getPositionInGroup(), field.getMaxCount(), field.getLength(), field.getCatalog_id(),
                enabled, enabled ? field.getRequired() : false, field.getRole(), field.getTag());
    }

    public static FieldTo asTo(ValuedField valuedField, List<Role> curUserRoles) {
        List<FieldTo> childFields = new ArrayList<>();
        for (ValuedField childField : valuedField.getChildValuedField()) {
            childField.getField().setRole(valuedField.getField().getRole());
            childFields.add(asTo(childField, curUserRoles));
        }
        Field field = valuedField.getField();
        Boolean enabled = curUserRoles.contains(field.getRole());

        FieldTo fieldTo = new FieldTo(valuedField.getId(), field.getName(), childFields,
                field.getId(), field.getFieldType(), field.getPositionInGroup(), field.getMaxCount(), field.getLength(),
                field.getCatalog_id(), enabled, enabled ? field.getRequired() : false, field.getRole(), field.getTag());

        switch (field.getFieldType()) {
            case TEXT:
            case TEXTAREA:
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
            case DATETIME:
                fieldTo.setValueDate(valuedField.getValueDateTime());
                break;
            case CATALOG:
                if (valuedField.getCatalogElem() != null)
                    fieldTo.setValueInt(valuedField.getCatalogElem().getId());
        }
        return fieldTo;
    }
}
