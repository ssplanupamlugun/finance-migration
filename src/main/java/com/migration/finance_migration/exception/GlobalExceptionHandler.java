package com.migration.finance_migration.exception;

import com.migration.finance_migration.dto.response.ApiResponseDto;
import com.migration.finance_migration.exception.custom.MigrationFailedException;
import com.migration.finance_migration.exception.custom.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.migration.finance_migration.exception.custom.MigrationServiceNotImplementedException;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiResponseDto> handleWebClientResponseException(WebClientResponseException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(ex.getStatusCode().value())
                        .message("External API request failed.")
                        .error(ex.getResponseBodyAsString())
                        .build());
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<ApiResponseDto> handleWebClientRequestException(WebClientRequestException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .message("Unable to connect to the external service.")
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto> handleValidationException(MethodArgumentNotValidException ex) {

        String error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .orElse("Validation failed.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Validation failed.")
                        .error(error)
                        .build());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponseDto> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.PAYLOAD_TOO_LARGE.value())
                        .message("Uploaded file size exceeds the allowed limit.")
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponseDto> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Required request parameter is missing.")
                        .error(ex.getParameterName())
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDto> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid request body.")
                        .error(ex.getMostSpecificCause().getMessage())
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDto> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.CONFLICT.value())
                        .message("Database constraint violation.")
                        .error(ex.getMostSpecificCause().getMessage())
                        .build());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponseDto> handleIOException(IOException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Error while processing the uploaded file.")
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDto> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("An unexpected error occurred.")
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(MigrationFailedException.class)
    public ResponseEntity<ApiResponseDto> handleMigrationFailed(MigrationFailedException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(MigrationServiceNotImplementedException.class)
    public ResponseEntity<ApiResponseDto> handleMigrationServiceNotImplemented(MigrationServiceNotImplementedException ex) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.NOT_IMPLEMENTED.value())
                        .message(ex.getMessage())
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.builder()
                        .success(false)
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal server error.")
                        .error(ex.getMessage())
                        .build());
    }
}