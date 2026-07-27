package com.migration.finance_migration.exception.custom;

public class MigrationServiceNotImplementedException extends RuntimeException {

    public MigrationServiceNotImplementedException(String message) {
        super(message);
    }
}