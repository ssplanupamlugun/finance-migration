package com.migration.finance_migration.config;

import com.migration.finance_migration.entity.SheetMigration;
import com.migration.finance_migration.enums.MigrationStatus;
import com.migration.finance_migration.enums.SheetName;
import com.migration.finance_migration.repository.SheetMigrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SheetMigrationSyncRunner implements CommandLineRunner {

    private final SheetMigrationRepository repository;

    @Override
    public void run(String... args) {

        // Enum values
        Set<String> enumNames = Arrays.stream(SheetName.values())
                .map(SheetName::getValue)
                .collect(Collectors.toSet());

        // Insert missing rows
        for (String sheetName : enumNames) {

            repository.findBySheetNameIgnoreCase(sheetName)
                    .orElseGet(() -> repository.save(
                            SheetMigration.builder()
                                    .sheetName(sheetName)
                                    .supported(false)
                                    .status(MigrationStatus.PENDING)
                                    .build()
                    ));
        }

        // Delete rows that no longer exist in enum
        repository.findAll().stream()
                .filter(sheet -> !enumNames.contains(sheet.getSheetName()))
                .forEach(repository::delete);
    }
}