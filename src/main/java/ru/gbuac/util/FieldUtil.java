package ru.gbuac.util;

import ru.gbuac.model.*;
import ru.gbuac.to.FieldTo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FieldUtil {

    private FieldUtil() {
    }

    public static FieldTo asTo(Field field, List<String> curUserRoles, HashMap<Integer, FieldsStages> fMap) {
        List<FieldTo> childFields = new ArrayList<>();
        for (Field childField : field.getChildField()) {
            childFields.add(asTo(childField, curUserRoles, fMap));
        }
        FieldsStages fieldsStages = fMap.get(field.getId());
        boolean enabled = fieldsStages != null && curUserRoles.contains(fieldsStages.getRole().getAuthority());
        enabled = enabled || curUserRoles.contains("ROLE_ADMIN");

        Integer catalog_id = getCatalogId(field.getCatalog());
        Integer parentCatalog_id = getParentCatalogId(field.getCatalog());

        return new FieldTo(null, field.getName(), childFields, field.getId(), field.getFieldType(),
                field.getPositionInGroup(), field.getMaxCount(), field.getLength(), parentCatalog_id, catalog_id,
                enabled, enabled && (fieldsStages != null || fieldsStages.getRequired()), field.getTag());
    }

    private static Integer getCatalogId(Catalog catalog) {
        return catalog != null ? catalog.getId() : null;
    }

    private static Integer getParentCatalogId(Catalog catalog) {
        return catalog != null ? catalog.getParentCatalog() : null;
    }

    public static FieldTo asTo(ValuedField valuedField, List<String> curUserRoles, HashMap<Integer, FieldsStages> fMap) {
        List<FieldTo> childFields = new ArrayList<>();
        for (ValuedField childField : valuedField.getChildValuedField()) {
            childFields.add(asTo(childField, curUserRoles, fMap));
        }
        Field field = valuedField.getField();
        FieldsStages fieldsStages = fMap.get(field.getId());
        boolean enabled = fieldsStages != null && curUserRoles.contains(fieldsStages.getRole().getAuthority());
        enabled = enabled || curUserRoles.contains("ROLE_ADMIN");
        Integer catalog_id = getCatalogId(field.getCatalog());
        Integer parentCatalog_id = getParentCatalogId(field.getCatalog());

        FieldTo fieldTo = new FieldTo(valuedField.getId(), field.getName(), childFields,
                field.getId(), field.getFieldType(), field.getPositionInGroup(), field.getMaxCount(), field.getLength(),
                parentCatalog_id, catalog_id, enabled,
                enabled && (fieldsStages != null || fieldsStages.getRequired()), field.getTag());

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
