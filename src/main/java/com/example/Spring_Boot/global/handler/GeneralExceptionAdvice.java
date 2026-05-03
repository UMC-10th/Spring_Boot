package com.example.Spring_Boot.global.handler;

import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.BaseErrorCode;
import com.example.Spring_Boot.global.apiPayload.code.GeneralErrorCode;
import com.example.Spring_Boot.global.exception.ProjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GeneralExceptionAdvice {

    // 프로젝트 예외 처리 (UserException 등 모든 도메인 예외 포함)
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ApiResponse<Void>> handleProjectException(ProjectException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, null));
    }

    // 존재하지 않는 URI
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoHandlerFound(NoHandlerFoundException e) {
        BaseErrorCode code = GeneralErrorCode.NOT_FOUND;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, null));
    }

    // 지원하지 않는 HTTP 메서드
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        BaseErrorCode code = GeneralErrorCode.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, null));
    }

    // 필수 쿼리 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<String>> handleMissingParam(MissingServletRequestParameterException e) {
        BaseErrorCode code = GeneralErrorCode.BAD_REQUEST;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, e.getParameterName() + " 파라미터가 필요합니다."));
    }

    // 그 외 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, e.getMessage()));
    }
}