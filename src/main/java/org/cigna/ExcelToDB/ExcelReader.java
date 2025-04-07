package org.cigna.ExcelToDB;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private final String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    public SheetData readSheet(String sheetName) throws Exception {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new Exception("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            List<String> columns = new ArrayList<>();
            for (Cell cell : headerRow) {
                columns.add(cell.getStringCellValue());
            }

            List<List<Object>> rows = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                List<Object> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(switch (cell.getCellType()) {
                        case STRING -> cell.getStringCellValue();
                        case NUMERIC -> cell.getNumericCellValue();
                        case BOOLEAN -> cell.getBooleanCellValue();
                        default -> null;
                    });
                }
                rows.add(rowData);
            }

            return new SheetData(columns, rows);
        }
    }
}
