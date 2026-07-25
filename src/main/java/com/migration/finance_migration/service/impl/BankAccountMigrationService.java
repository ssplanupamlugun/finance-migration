package com.migration.finance_migration.service.impl;

import com.migration.finance_migration.dto.excel.BankAccountExcelDto;
import com.migration.finance_migration.dto.request.BankAccountRequestDto;
import com.migration.finance_migration.dto.response.MigrationSummaryDto;
import com.migration.finance_migration.enums.MigrationStatus;
import com.migration.finance_migration.mapper.BankAccountMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import com.migration.finance_migration.service.ApiService;
import com.migration.finance_migration.service.SheetMigrationService;
import com.migration.finance_migration.util.BankAccountExcelReader;
import com.migration.finance_migration.util.ExcelUtil;
import com.migration.finance_migration.dto.response.ApiResponseDto;
import com.migration.finance_migration.enums.SheetName;
import com.migration.finance_migration.service.SheetMigrationStatusService;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankAccountMigrationService implements SheetMigrationService {

    private final BankAccountMapper bankAccountMapper;
    private final ApiService apiService;
    private final SheetMigrationStatusService sheetMigrationStatusService;

    @Override
    public SheetName getSheetName() {
        return SheetName.BANK_ACCOUNT;
    }

    @Override
    public MigrationSummaryDto migrate(Sheet sheet, String sessionId) {

        List<BankAccountRequestDto> requests = new ArrayList<>();

        int total = 0;
        for (Row row : sheet) {

            if (row.getRowNum() < 3 || ExcelUtil.isRowEmpty(row)) {
                continue;
            }

            total++;

            try {

                BankAccountExcelDto excelDto = BankAccountExcelReader.read(row);

                log.info("==================================================");
                log.info("Processing Row {}", row.getRowNum() + 1);
                log.info("Excel DTO : {}", excelDto);

                BankAccountRequestDto request = bankAccountMapper.toRequest(excelDto);

                log.info("Mapped Request : {}", request);

                requests.add(request);

            } catch (Exception ex) {

                log.error("Row {} failed", row.getRowNum() + 1, ex);
            }
        }

        // Call API once
        ApiResponseDto apiResponse = apiService.createBankAccount(requests, sessionId);

        // Update sheet migration status
        sheetMigrationStatusService.updateSheetMigration(
                sheet.getSheetName(),
                apiResponse.isSuccess() ? MigrationStatus.COMPLETED : MigrationStatus.FAILED,
                apiResponse.getMessage());

        return new MigrationSummaryDto(
                sheet.getSheetName(),
                total,
                apiResponse.isSuccess(),
                apiResponse.getMessage());
    }
}
