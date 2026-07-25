package com.migration.finance_migration.util;

public final class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    /* =========================
     * Excel Sheet Names
     * ========================= */
    public static final String SHEET_BANK_ACCOUNT = "Bank Account";

    /* =========================
     * HTTP
     * ========================= */
    public static final String COOKIE_SESSION_ID = "SESSIONID";

    /* =========================
     * Common Messages
     * ========================= */
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";

    public static final String MSG_MIGRATION_SUCCESS = "Migration completed successfully.";
    public static final String MSG_MIGRATION_FAILED = "Migration failed.";
    public static final String MSG_INVALID_FILE = "Invalid Excel file.";
    public static final String MSG_EMPTY_FILE = "Uploaded file is empty.";
    public static final String MSG_SHEET_NOT_FOUND = "Sheet not found.";

    /* =========================
     * File
     * ========================= */
    public static final String XLS = ".xls";
    public static final String XLSX = ".xlsx";

    /* =========================
     * View Names
     * ========================= */
    public static final String VIEW_INDEX = "index";
    public static final String VIEW_UPLOAD = "upload";
    public static final String VIEW_RESULT = "result";
    public static final String VIEW_HISTORY = "history";
}