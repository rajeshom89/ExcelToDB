package org.cigna.ExcelToDB;

import java.sql.*;
import java.util.List;

public class DatabaseInserter {
    private static final String URL = "jdbc:mysql://localhost:3306/student";
    private static final String USER = "rajesh";
    private static final String PASSWORD = "Roopa@123";

    public static void insertData(String tableName, SheetData sheetData, String sql) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (List<Object> row : sheetData.getRows()) {
                for (int i = 0; i < row.size(); i++) {
                    stmt.setObject(i + 1, row.get(i));
                }
                stmt.addBatch();
            }

            stmt.executeBatch();
            System.out.println("Data inserted into table: " + tableName);
        }
    }
}
