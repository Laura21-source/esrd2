package ru.gbuac.util;

import ru.gbuac.model.Role;

import java.util.ArrayList;
import java.util.List;

public class RolesUtil {
    private RolesUtil() {
    }

    public static List<Role> getPlainList(List<Role> roles) {
        List<Role> plainList = new ArrayList<>();
        addToPlainList(plainList, roles);
        return plainList;
    }

    private static void addToPlainList(List<Role> plainList, List<Role> roles) {
        for (Role role : roles) {
            plainList.add(role);
            addToPlainList(plainList, role.getChildRole());
        }
    }
}
