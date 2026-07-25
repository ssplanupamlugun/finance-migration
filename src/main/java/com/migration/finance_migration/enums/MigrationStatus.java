package com.migration.finance_migration.enums;

public enum MigrationStatus {

    PENDING("Pending", "Migration has not started yet"),
    COMPLETED("Completed", "Migration completed successfully"),
    FAILED("Failed", "Migration failed");

    private final String name;
    private final String description;

    MigrationStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}