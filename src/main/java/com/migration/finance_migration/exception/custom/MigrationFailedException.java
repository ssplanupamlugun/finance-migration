package com.migration.finance_migration.exception.custom;

public class MigrationFailedException extends RuntimeException {

    public MigrationFailedException(String message) {
        super(message);
    }
    
}
