package com.migration.finance_migration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto {

    private boolean success;

    private int statusCode;

    private String message;

    private Object data;

    private String error;
}