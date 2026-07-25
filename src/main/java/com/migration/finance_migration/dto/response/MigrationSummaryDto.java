package com.migration.finance_migration.dto.response;

public record MigrationSummaryDto(

        String sheetName,
        int totalRecords,
        boolean isSuccess,
        String status

) {
}