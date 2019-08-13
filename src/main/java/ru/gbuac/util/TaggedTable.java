package ru.gbuac.util;

import java.util.HashMap;
import java.util.List;

public class TaggedTable {
    String tableName;
    List<TableRow> rows;

    public TaggedTable(String tableName, List<TableRow> rows) {
        this.tableName = tableName;
        this.rows = rows;
    }
}
