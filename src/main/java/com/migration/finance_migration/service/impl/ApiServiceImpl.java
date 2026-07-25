package com.migration.finance_migration.service.impl;

import com.migration.finance_migration.client.MigrationApiClient;
import com.migration.finance_migration.config.MigrationConfig;
import com.migration.finance_migration.dto.request.BankAccountRequestDto;
import com.migration.finance_migration.dto.response.ApiResponseDto;
import com.migration.finance_migration.service.ApiService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final MigrationApiClient migrationApiClient;
    private final MigrationConfig migrationConfig;

    @Override
    public ApiResponseDto createBankAccount(List<BankAccountRequestDto> request,
                                            String sessionId) {

        return migrationApiClient.post(
                migrationConfig.getBankAccountCreateUrl(),
                request,
                sessionId
        );
    }
}