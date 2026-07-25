package com.migration.finance_migration.service.impl;
import com.migration.finance_migration.entity.SheetMigration;
import com.migration.finance_migration.enums.MigrationStatus;
import com.migration.finance_migration.repository.SheetMigrationRepository;
import com.migration.finance_migration.service.SheetMigrationStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SheetMigrationStatusServiceImpl
        implements SheetMigrationStatusService {

    private final SheetMigrationRepository repository;

    @Override
    public SheetMigration updateSheetMigration(
            String sheetName,
            MigrationStatus status,
            String message) {

        SheetMigration sheetMigration = repository
                .findBySheetNameIgnoreCase(sheetName)
                .orElseThrow(() -> new RuntimeException("Sheet migration not found: " + sheetName));

        sheetMigration.setStatus(status);
        sheetMigration.setMessage(message);

        return repository.save(sheetMigration);
    }

    @Override
    public SheetMigration getSheetMigration() {
        return repository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No sheet migration found"));
    }
}