package com.example.Dily_serialu;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DilySerialuApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DilySerialuApplication.class, args);

        String filePath = "C:/Users/julie/Downloads/řazení.xlsx";

        try (InputStream inputFile = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputFile)) {

            List<String> serialParts1 = new ArrayList<>();
            Sheet list1 = workbook.getSheetAt(0);
            for (Row row : list1) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    String value = getCellValue(cell);
                    if (!value.isEmpty()) {
                        serialParts1.add(value);
                    }
                }
            }

            List<String> serialParts2 = new ArrayList<>();
            Sheet list2 = workbook.getSheetAt(1);
            for (Row row : list2) {
                Cell cell1 = row.getCell(0);
                Cell cell2 = row.getCell(1);

                if (cell1 != null && cell1.getCellType() == CellType.STRING && cell2 != null) {
                    String part1 = cell1.getStringCellValue().trim();
                    String part2 = "";

                    if (cell2.getCellType() == CellType.NUMERIC) {
                        part2 = String.valueOf((int) cell2.getNumericCellValue());
                    }
                    serialParts2.add(part1 + " " + part2);
                }
            }

            serialParts1.sort(new PartsComparator());
            serialParts2.sort(new PartsComparator());

            System.out.println("\nSeznám dílů 1.seriálu (" + list1.getSheetName() + "): ");
            serialParts1.forEach(System.out::println);

            System.out.println("\nSeznám dílů 2.seriálu (" + list2.getSheetName() + "): ");
            serialParts2.forEach(System.out::println);

        } catch (IOException ex) {
            System.out.println("Chyba při čtení souboru: " + ex.getMessage());
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        }
        return "";
    }
}