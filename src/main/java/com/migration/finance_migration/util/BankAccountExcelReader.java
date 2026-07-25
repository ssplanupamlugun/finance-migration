package com.migration.finance_migration.util;

import com.migration.finance_migration.dto.excel.BankAccountExcelDto;
import org.apache.poi.ss.usermodel.Row;

public final class BankAccountExcelReader {

    private BankAccountExcelReader() {
    }

    public static BankAccountExcelDto read(Row row) {

        return BankAccountExcelDto.builder()
                .rowNumber(row.getRowNum() + 1)
                .bankBranch(ExcelUtil.getCellValue(row.getCell(2)))
                .ifscCode(ExcelUtil.getCellValue(row.getCell(3)))
                .accountNumber(ExcelUtil.getCellValue(row.getCell(4)))
                .fund(ExcelUtil.getCellValue(row.getCell(5)))
                .accountType(ExcelUtil.getCellValue(row.getCell(6)))
                .description(ExcelUtil.getCellValue(row.getCell(7)))
                .payTo(ExcelUtil.getCellValue(row.getCell(8)))
                .usageType(ExcelUtil.getCellValue(row.getCell(9)))
                .build();
    }
}