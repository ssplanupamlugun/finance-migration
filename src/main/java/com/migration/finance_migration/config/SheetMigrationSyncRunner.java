package com.migration.finance_migration.config;

import com.migration.finance_migration.entity.SheetMigration;
import com.migration.finance_migration.enums.MigrationStatus;
import com.migration.finance_migration.enums.SheetName;
import com.migration.finance_migration.repository.SheetMigrationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SheetMigrationSyncRunner implements CommandLineRunner {

        private final SheetMigrationRepository repository;

        @Override
        public void run(String... args) {

                log.info("========== SheetMigrationSyncRunner Started ==========");

                // Enum values
                Set<String> enumNames = Arrays.stream(SheetName.values())
                                .map(SheetName::getValue)
                                .collect(Collectors.toSet());

                log.info("Enum sheet names: {}", enumNames);

                // Insert missing rows
                for (String sheetName : enumNames) {

                        log.info("Checking sheet: {}", sheetName);

                        repository.findBySheetNameIgnoreCase(sheetName)
                                        .orElseGet(() -> {
                                                log.info("Sheet '{}' not found. Inserting...", sheetName);

                                                SheetMigration saved = repository.save(
                                                                SheetMigration.builder()
                                                                                .sheetName(sheetName)
                                                                                .supported(false)
                                                                                .status(MigrationStatus.PENDING)
                                                                                .build());

                                                log.info("Inserted sheet: {}", saved.getSheetName());

                                                return saved;
                                        });
                }

                // Delete rows that no longer exist in enum
                repository.findAll().stream()
                                .filter(sheet -> !enumNames.contains(sheet.getSheetName()))
                                .forEach(sheet -> {
                                        log.warn("Deleting sheet not present in enum: {}", sheet.getSheetName());
                                        repository.delete(sheet);
                                });

                log.info("========== SheetMigrationSyncRunner Completed ==========");
        }
}