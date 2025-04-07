package org.cigna.ExcelToDB;

import org.cigna.ExcelToDB.DatabaseInserter;
import org.cigna.ExcelToDB.ExcelReader;
import org.cigna.ExcelToDB.SQLBuilder;
import org.cigna.ExcelToDB.SheetData;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Main <ExcelFilePath> <SheetName> [<TableName>]");
            return;
        }

        String filePath = args[0];
        String sheetName = args[1];
        String tableName = (args.length >= 3) ? args[2] : sheetName;

        try {
            ExcelReader reader = new ExcelReader(filePath);
            SheetData sheetData = reader.readSheet(sheetName);

            String sql = SQLBuilder.buildInsertQuery(tableName, sheetData.getColumns());
            DatabaseInserter.insertData(tableName, sheetData, sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
