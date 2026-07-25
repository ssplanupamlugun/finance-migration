package com.migration.finance_migration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.migration.finance_migration.enums.MigrationStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "sheet_migration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SheetMigration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sheet_name", nullable = false, unique = true, length = 100)
    private String sheetName;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    @Builder.Default
    private Boolean supported = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "migration_status")
    @Builder.Default
    private MigrationStatus status = MigrationStatus.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}