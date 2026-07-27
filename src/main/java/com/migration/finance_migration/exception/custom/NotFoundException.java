package com.migration.finance_migration.exception.custom;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}