package com.migration.finance_migration.service;

import com.migration.finance_migration.dto.response.MigrationSummaryDto;

import org.apache.poi.ss.usermodel.Sheet;

import com.migration.finance_migration.enums.SheetName;

public interface SheetMigrationService {

    SheetName getSheetName();

    MigrationSummaryDto migrate(
            Sheet sheet,
            String sessionId);


}