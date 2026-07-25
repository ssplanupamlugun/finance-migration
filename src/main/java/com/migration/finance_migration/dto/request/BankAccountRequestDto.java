package com.migration.finance_migration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountRequestDto {

    private String bankBranchName;

    private String fund;

    private String accountNumber;

    private String ifscCode;

    private String accountType;

    private String narration;

    private String payTo;

    private String type;

}