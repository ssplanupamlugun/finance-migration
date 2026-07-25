package com.migration.finance_migration.enums;

public enum SheetName {

    BANK_ACCOUNT("Bank Account");

    private final String value;

    SheetName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SheetName fromValue(String value) {
        for (SheetName sheetName : values()) {
            if (sheetName.value.equalsIgnoreCase(value)) {
                return sheetName;
            }
        }
        throw new IllegalArgumentException("Invalid sheet name: " + value);
    }
}