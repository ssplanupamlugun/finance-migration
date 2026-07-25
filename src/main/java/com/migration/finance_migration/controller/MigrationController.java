package com.migration.finance_migration.controller;

import com.migration.finance_migration.dto.response.MigrationSummaryDto;
import com.migration.finance_migration.service.MigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/migration")
@RequiredArgsConstructor
public class MigrationController {

    private final MigrationService migrationService;

    @PostMapping("/upload")
    public ResponseEntity<List<MigrationSummaryDto>> uploadWorkbook(
            @RequestParam("file") MultipartFile file,
            @RequestParam("sessionId") String sessionId) throws IOException {

        List<MigrationSummaryDto> response =
                migrationService.migrateWorkbook(file, sessionId);

        return ResponseEntity.ok(response);
    }
}