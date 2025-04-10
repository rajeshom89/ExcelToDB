package org.cigna.ExcelToDB;

import java.util.List;

public class SQLBuilder {
    public static String buildInsertQuery(String tableName, List<String> columns) {
        StringBuilder sql = new StringBuilder("INSERT IGNORE INTO ")
                .append(tableName.toLowerCase())
                .append(" (")
                .append(String.join(", ", columns))
                .append(") VALUES (");

        sql.append("?,".repeat(columns.size()));
        sql.setLength(sql.length() - 1); // remove last comma
        sql.append(")");

        return sql.toString();
    }
}
