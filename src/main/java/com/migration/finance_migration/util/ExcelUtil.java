package com.migration.finance_migration.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public final class ExcelUtil {

    private ExcelUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates workbook from uploaded excel stream.
     */
    public static Workbook getWorkbook(InputStream inputStream) throws IOException {
        return new XSSFWorkbook(inputStream);
    }

    /**
     * Returns sheet by name.
     */
    public static Sheet getSheet(Workbook workbook, String sheetName) {
        return workbook.getSheet(sheetName);
    }

    /**
     * Returns trimmed String value from a cell.
     */
    public static String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }

        return switch (cell.getCellType()) {

            case STRING -> cell.getStringCellValue().trim();

            case NUMERIC -> {

                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getLocalDateTimeCellValue().toString();
                }

                double value = cell.getNumericCellValue();

                if (value == (long) value) {
                    yield String.valueOf((long) value);
                }

                yield String.valueOf(value);
            }

            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());

            case FORMULA -> {

                FormulaEvaluator evaluator =
                        cell.getSheet()
                                .getWorkbook()
                                .getCreationHelper()
                                .createFormulaEvaluator();

                CellValue evaluated = evaluator.evaluate(cell);

                yield switch (evaluated.getCellType()) {

                    case STRING -> evaluated.getStringValue();

                    case NUMERIC -> {

                        double value = evaluated.getNumberValue();

                        if (value == (long) value) {
                            yield String.valueOf((long) value);
                        }

                        yield String.valueOf(value);
                    }

                    case BOOLEAN -> String.valueOf(evaluated.getBooleanValue());

                    default -> "";
                };
            }

            case BLANK, _NONE, ERROR -> "";
        };
    }

    /**
     * Returns true if row is empty.
     */
    public static boolean isRowEmpty(Row row) {

        if (row == null) {
            return true;
        }

        for (Cell cell : row) {
            if (!getCellValue(cell).isBlank()) {
                return false;
            }
        }

        return true;
    }
}