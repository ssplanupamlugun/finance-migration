package com.migration.finance_migration.service;

import com.migration.finance_migration.dto.response.MigrationSummaryDto;
import org.apache.poi.ss.usermodel.Sheet;

public interface SheetMigrationService {

    String getSheetName();

    MigrationSummaryDto migrate(
            Sheet sheet,
            String sessionId
    );

}