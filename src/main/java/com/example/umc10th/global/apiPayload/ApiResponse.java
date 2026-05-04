package com.example.umc10th.global.apiPayload;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@JsonPropertyOrder({"isSuccess","code","message","result"})
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    // 성공 응답 (result 포함)
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    // 성공 응답 (result 없음)
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), null);
    }

    // 실패 응답
    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T result) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), result);
    }
    
}

