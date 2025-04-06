package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public static void main (String [] args) throws IOException, IOException {
        List<String> sheetHeaders = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("Students.xlsx");
        assert is != null;
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        Row headerRow = workbook.getSheetAt(0).getRow(0);
        for(int i=0; i<headerRow.getLastCellNum();i++)
            sheetHeaders.add(headerRow.getCell(i).getStringCellValue());

        sheetHeaders.stream().forEach(System.out::println);
    }
}