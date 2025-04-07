package org.cigna.ExcelToDB;

import java.util.List;

public class SheetData {
    private final List<String> columns;
    private final List<List<Object>> rows;

    public SheetData(List<String> columns, List<List<Object>> rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<List<Object>> getRows() {
        return rows;
    }
}
