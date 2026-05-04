package com.example.Spring_Boot.global.apiPayload;

import com.example.Spring_Boot.global.apiPayload.code.BaseErrorCode;
import com.example.Spring_Boot.global.apiPayload.code.BaseSuccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    private final T result;

    // 성공 응답 (result 있음)
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode successCode, T result) {
        return ApiResponse.<T>builder()
                .isSuccess(true)
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .result(result)
                .build();
    }

    // 성공 응답 (result 없음)
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode successCode) {
        return ApiResponse.<T>builder()
                .isSuccess(true)
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .result(null)
                .build();
    }

    // 실패 응답
    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, T result) {
        return ApiResponse.<T>builder()
                .isSuccess(false)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .result(result)
                .build();
    }
}