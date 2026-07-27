package com.migration.finance_migration.service.impl;

import com.migration.finance_migration.entity.SheetMigration;
import com.migration.finance_migration.enums.MigrationStatus;
import com.migration.finance_migration.repository.SheetMigrationRepository;
import com.migration.finance_migration.service.SheetMigrationStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.migration.finance_migration.exception.custom.NotFoundException;
import java.util.List;

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
                .orElseThrow(() -> new NotFoundException("Sheet migration not found: " + sheetName));

        sheetMigration.setStatus(status);
        sheetMigration.setMessage(message);

        return repository.save(sheetMigration);
    }

    @Override
    public List<SheetMigration> getSheetMigration() {
        return repository.findAll();
    }
}