package com.example.umc10th.global.exception.advice;

import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.status.GeneralErrorCode;
import com.example.umc10th.global.exception.ProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

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
    
    // 2. 시큐리티 인증 실패 (401)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException e) {
        log.warn("인증 실패: {}", e.getMessage());
        BaseErrorCode code = GeneralErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(code.getStatus()).body(ApiResponse.onFailure(code, null));
    }

    // 2. 시큐리티 인가 실패 (403)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("인가 실패: {}", e.getMessage());
        BaseErrorCode code = GeneralErrorCode.FORBIDDEN;
        return ResponseEntity.status(code.getStatus()).body(ApiResponse.onFailure(code, null));
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

    // 3. 필수 요청 헤더 누락 - 예: @RequestHeader("Authorization") 빼먹은 경우
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiResponse<String>> handleMissingHeader(
            MissingRequestHeaderException e) {
        log.warn("필수 헤더 누락: {}", e.getHeaderName());
        BaseErrorCode code = GeneralErrorCode.MISSING_HEADER;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, "누락된 헤더: " + e.getHeaderName()));
    }

    // 4. 필수 쿼리 파라미터 누락 - 예: @RequestParam("id") 빼먹은 경우
    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<String>> handleMissingParameter(
            MissingServletRequestParameterException e) {
        log.warn("필수 쿼리 파라미터 누락: {}", e.getParameterName());
        BaseErrorCode code = GeneralErrorCode.MISSING_PARAMETER;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, "누락된 파라미터: " + e.getParameterName()));
    }

    // 5. 타입 미스매치 - ?page=abc, ?sortBy=BANANA 같은 경우
    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleTypeMismatch(
            MethodArgumentTypeMismatchException e) {
        String requiredType = e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "알 수 없음";
        log.warn("타입 미스매치 — {}={}, 기대 타입={}", e.getName(), e.getValue(),requiredType);
        BaseErrorCode code = GeneralErrorCode.TYPE_MISMATCH;
        String detail = String.format("%s 값 '%s'을(를) %s 타입으로 변환할 수 없습니다.",
                e.getName(), e.getValue(), requiredType);

        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, detail));
    }

    // 6. JSON 파싱 실패 - 잘못된 JSON 본문, Body 누락 - 예: { "rating": } 처럼 깨진 JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidJson(
            HttpMessageNotReadableException e) {
        log.warn("JSON 파싱 실패: {}", e.getMessage());
        BaseErrorCode code = GeneralErrorCode.INVALID_JSON;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, null));
    }

    // 7. 지원하지 않는 HTTP 메서드 - 예: GET /api/items (POST만 지원) 같은 경우
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException e) {
        log.warn("지원하지 않는 HTTP 메서드: {}", e.getMethod());
        BaseErrorCode code = GeneralErrorCode.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, "지원하지 않는 HTTP 메서드: " + e.getMethod()));

    }

    // 8. 그 외 모든 예외 - 예상치 못한 서버 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        log.error("예상치 못한 예외 발생", e);
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, e.getMessage()));
    }
}