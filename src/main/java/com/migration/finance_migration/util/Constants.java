package com.migration.finance_migration.util;

public final class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    /*
     * =========================
     * HTTP
     * =========================
     */
    public static final String COOKIE_SESSION_ID = "SESSIONID";

    public static final String SHEET_NOT_CONFIGURED = "Skipped (Sheet not configured)";
    public static final String SHEET_NOT_SUPPORTED = "Skipped (Sheet not supported)";
    public static final String NO_MIGRATION_SERVICE = "Skipped (No Migration Service)";
    public static final String SHEET_ALREADY_MIGRATED = "Skipped (Sheet already migrated)";
}