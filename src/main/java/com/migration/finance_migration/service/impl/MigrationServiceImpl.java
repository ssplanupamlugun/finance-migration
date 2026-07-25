package com.migration.finance_migration.service.impl;

import com.migration.finance_migration.dto.response.MigrationSummaryDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.migration.finance_migration.service.MigrationService;
import com.migration.finance_migration.service.SheetMigrationService;

@Service
@RequiredArgsConstructor
public class MigrationServiceImpl implements  MigrationService {

    private final List<SheetMigrationService> migrationServices;

    @Override
    public List<MigrationSummaryDto> migrateWorkbook(
            MultipartFile file,
            String sessionId
    ) throws IOException {

        Map<String, SheetMigrationService> serviceMap =
                migrationServices.stream()
                        .collect(Collectors.toMap(
                                service -> service.getSheetName().toLowerCase(),
                                Function.identity()
                        ));

        List<MigrationSummaryDto> summaries = new ArrayList<>();

        try (Workbook workbook =
                     WorkbookFactory.create(file.getInputStream())) {

            for (Sheet sheet : workbook) {

                String sheetName = sheet.getSheetName().trim().toLowerCase();

                SheetMigrationService service = serviceMap.get(sheetName);

                if (service != null) {

                    summaries.add(
                            service.migrate(sheet, sessionId)
                    );

                } else {

                    summaries.add(
                            new MigrationSummaryDto(
                                    sheet.getSheetName(),
                                    0,
                                    0,
                                    0,
                                    "Skipped (No Migration Service)"
                            )
                    );

                }
            }

        }

        return summaries;
    }
}