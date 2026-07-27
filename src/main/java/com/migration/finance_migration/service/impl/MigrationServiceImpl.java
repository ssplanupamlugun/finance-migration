package com.migration.finance_migration.service.impl;

import com.migration.finance_migration.dto.response.MigrationSummaryDto;
import com.migration.finance_migration.entity.SheetMigration;
import com.migration.finance_migration.enums.MigrationStatus;
import com.migration.finance_migration.repository.SheetMigrationRepository;
import com.migration.finance_migration.service.MigrationService;
import com.migration.finance_migration.service.SheetMigrationService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.migration.finance_migration.exception.custom.MigrationServiceNotImplementedException;
import com.migration.finance_migration.util.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MigrationServiceImpl implements MigrationService {

        private final List<SheetMigrationService> migrationServices;
        private final SheetMigrationRepository sheetMigrationRepository;

        @Override
        public List<MigrationSummaryDto> migrateWorkbook(
                        MultipartFile file,
                        String sessionId) throws IOException {

                Map<String, SheetMigrationService> serviceMap = migrationServices.stream()
                                .collect(Collectors.toMap(
                                                service -> service.getSheetName().name().replace("_", " ")
                                                                .toLowerCase(),
                                                Function.identity()));

                Map<String, SheetMigration> sheetConfigMap = getSheetConfigMap();

                List<MigrationSummaryDto> summaries = new ArrayList<>();

                try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

                        serviceMap.forEach((key, value) -> log.info("Service registered for: {}", key));

                        validateWorkbook(workbook, serviceMap, sheetConfigMap);

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
                                                                        Constants.SHEET_NOT_CONFIGURED));
                                        continue;
                                }

                                // Sheet configured but disabled
                                if (!Boolean.TRUE.equals(sheetConfig.getSupported())) {

                                        summaries.add(
                                                        new MigrationSummaryDto(
                                                                        sheet.getSheetName(),
                                                                        0,
                                                                        false,
                                                                        Constants.SHEET_NOT_SUPPORTED));
                                        continue;
                                }

                                // Sheet Allready migrated
                                if (MigrationStatus.COMPLETED.equals(sheetConfig.getStatus())) {

                                        summaries.add(
                                                        new MigrationSummaryDto(
                                                                        sheet.getSheetName(),
                                                                        0,
                                                                        false,
                                                                        Constants.SHEET_ALREADY_MIGRATED));
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
                                                                        Constants.NO_MIGRATION_SERVICE));
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

        private void validateWorkbook(
                        Workbook workbook,
                        Map<String, SheetMigrationService> serviceMap,
                        Map<String, SheetMigration> configMap) {

                boolean serviceFound = false;
                boolean enabledSheetFound = false;

                for (Sheet sheet : workbook) {

                        String sheetName = sheet.getSheetName().trim().toLowerCase();

                        if (!serviceMap.containsKey(sheetName)) {
                                continue;
                        }

                        serviceFound = true;

                        SheetMigration config = configMap.get(sheetName);

                        if (config == null) {
                                continue;
                        }

                        if (Boolean.TRUE.equals(config.getSupported())) {
                                enabledSheetFound = true;
                                break;
                        }

                }

                if (!serviceFound) {
                        throw new MigrationServiceNotImplementedException(
                                        "No migration service found for any sheet in the uploaded workbook.");
                }

                if (!enabledSheetFound) {
                        throw new MigrationServiceNotImplementedException(
                                        "All matched sheets are not supported for migration.");
                }

        }

}