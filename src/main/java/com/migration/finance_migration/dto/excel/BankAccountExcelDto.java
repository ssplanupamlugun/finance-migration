package com.migration.finance_migration.dto.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountExcelDto {

    private Integer rowNumber;

    private String bankBranch;
    private String ifscCode;
    private String accountNumber;
    private String fund;
    private String accountType;
    private String description;
    private String payTo;
    private String usageType;
}