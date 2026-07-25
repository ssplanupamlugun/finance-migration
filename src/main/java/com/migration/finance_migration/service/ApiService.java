package com.migration.finance_migration.service;

import com.migration.finance_migration.dto.request.BankAccountRequestDto;
import com.migration.finance_migration.dto.response.ApiResponseDto;
import java.util.List;

public interface ApiService {

    ApiResponseDto createBankAccount(List<BankAccountRequestDto> request,
                                     String sessionId);

}