package com.migration.finance_migration.service;

import com.migration.finance_migration.entity.SheetMigration;
import com.migration.finance_migration.enums.MigrationStatus;

public interface SheetMigrationStatusService {

    SheetMigration updateSheetMigration(
            String sheetName,
            MigrationStatus status,
            String message);

    SheetMigration getSheetMigration();
}