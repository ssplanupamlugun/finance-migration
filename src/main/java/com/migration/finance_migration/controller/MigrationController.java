package com.migration.finance_migration.controller;

import com.migration.finance_migration.dto.response.ApiResponseDto;
import com.migration.finance_migration.dto.response.MigrationSummaryDto;
import com.migration.finance_migration.service.MigrationService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.migration.finance_migration.exception.custom.MigrationFailedException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/migration")
@RequiredArgsConstructor
public class MigrationController {

    private final MigrationService migrationService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponseDto> uploadWorkbook(
            @RequestParam("file") MultipartFile file,
            @RequestParam("sessionId") String sessionId) throws IOException {

        List<MigrationSummaryDto> response = migrationService.migrateWorkbook(file, sessionId);

        long successCount = response.stream()
                .filter(MigrationSummaryDto::isSuccess)
                .count();

        if (successCount == 0) {
            throw new MigrationFailedException("Migration failed. No sheet was migrated successfully.");
        }

        String message = successCount == response.size()
                ? "Migration completed successfully"
                : "Migration completed with partial success";

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message(message)
                        .data(response)
                        .build());
    }
}