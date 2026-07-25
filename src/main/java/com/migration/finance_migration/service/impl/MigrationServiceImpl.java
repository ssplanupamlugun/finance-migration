package com.migration.finance_migration.service.impl;

import com.migration.finance_migration.dto.response.MigrationSummaryDto;
import com.migration.finance_migration.entity.SheetMigration;
import com.migration.finance_migration.repository.SheetMigrationRepository;
import com.migration.finance_migration.service.MigrationService;
import com.migration.finance_migration.service.SheetMigrationService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.migration.finance_migration.enums.SheetName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MigrationServiceImpl implements MigrationService {

        private final List<SheetMigrationService> migrationServices;
        private final SheetMigrationRepository sheetMigrationRepository;

        @Override
        public List<MigrationSummaryDto> migrateWorkbook(
                        MultipartFile file,
                        String sessionId) throws IOException {

                Map<SheetName, SheetMigrationService> serviceMap = migrationServices.stream()
                                .collect(Collectors.toMap(
                                                SheetMigrationService::getSheetName,
                                                Function.identity()));

                Map<String, SheetMigration> sheetConfigMap = getSheetConfigMap();

                List<MigrationSummaryDto> summaries = new ArrayList<>();

                try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

                        for (Sheet sheet : workbook) {

                                String sheetName = sheet.getSheetName().trim().toLowerCase();

                                SheetMigration sheetConfig = sheetConfigMap.get(sheetName);

                                // Sheet not configured
                                if (sheetConfig == null) {
                                        summaries.add(
                                                        new MigrationSummaryDto(
                                                                        sheet.getSheetName(),
                                                                        0,
                                                                        false,
                                                                        "Skipped (Sheet not configured)"));
                                        continue;
                                }

                                // Sheet configured but disabled
                                if (!Boolean.TRUE.equals(sheetConfig.getSupported())) {

                                        summaries.add(
                                                        new MigrationSummaryDto(
                                                                        sheet.getSheetName(),
                                                                        0,
                                                                        false,
                                                                        "Skipped (Sheet not supported)"));
                                        continue;
                                }

                                // Migration service not implemented
                                SheetMigrationService service = serviceMap.get(sheetName);

                                if (service == null) {

                                        summaries.add(
                                                        new MigrationSummaryDto(
                                                                        sheet.getSheetName(),
                                                                        0,
                                                                        false,
                                                                        "Skipped (No Migration Service)"));
                                        continue;
                                }

                                // Execute migration
                                try {

                                        MigrationSummaryDto summary = service.migrate(sheet, sessionId);

                                        summaries.add(summary);

                                } catch (Exception ex) {

                                        summaries.add(
                                                        new MigrationSummaryDto(
                                                                        sheet.getSheetName(),
                                                                        0,
                                                                        false,
                                                                        ex.getMessage()));
                                }
                        }
                }

                return summaries;
        }

        private Map<String, SheetMigration> getSheetConfigMap() {
                return sheetMigrationRepository.findAll()
                                .stream()
                                .collect(Collectors.toMap(
                                                sheet -> sheet.getSheetName().trim().toLowerCase(),
                                                Function.identity()));
        }

}