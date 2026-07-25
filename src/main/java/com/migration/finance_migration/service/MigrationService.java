package com.migration.finance_migration.service;

import com.migration.finance_migration.dto.response.MigrationSummaryDto;
import com.migration.finance_migration.entity.SheetMigration;
import com.migration.finance_migration.enums.MigrationStatus;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MigrationService {

    List<MigrationSummaryDto> migrateWorkbook(MultipartFile file,
            String sessionId) throws IOException;

}