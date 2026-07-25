package com.migration.finance_migration.repository;

import com.migration.finance_migration.entity.SheetMigration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SheetMigrationRepository extends JpaRepository<SheetMigration, Long> {

    List<SheetMigration> findBySupportedTrue();

    Optional<SheetMigration> findBySheetNameIgnoreCase(String sheetName);

    boolean existsBySheetNameIgnoreCase(String sheetName);

}