package com.migration.finance_migration.mapper;

import com.migration.finance_migration.dto.excel.BankAccountExcelDto;
import com.migration.finance_migration.dto.request.BankAccountRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    @Mapping(target = "bankBranchName", source = "bankBranch")
    @Mapping(target = "fund", source = "fund")
    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "ifscCode", source = "ifscCode")
    @Mapping(target = "accountType", source = "accountType")
    @Mapping(target = "narration", source = "description")
    @Mapping(target = "payTo", source = "payTo")
    @Mapping(target = "type", source = "usageType")
    BankAccountRequestDto toRequest(BankAccountExcelDto excelDto);

}