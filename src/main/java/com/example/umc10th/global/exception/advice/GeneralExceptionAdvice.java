package com.example.umc10th.global.exception.advice;

import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.status.GeneralErrorCode;
import com.example.umc10th.global.exception.ProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionAdvice {

    // 1. 프로젝트 예외 (도메인 예외 포함)
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ApiResponse<Void>> handleProjectException(ProjectException e) {
        log.warn("ProjectException 발생: {}", e.getMessage());
        BaseErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, null));
    }

    // 2. @Valid 검증 실패 (DTO에 붙인 @NotBlank 등)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        BaseErrorCode code = GeneralErrorCode.VALIDATION_ERROR;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, errors));
    }

    // 3. 그 외 예상하지 못한 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        log.error("예상치 못한 예외 발생", e);
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, e.getMessage()));
    }
}