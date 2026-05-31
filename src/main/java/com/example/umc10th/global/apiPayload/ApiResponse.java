package com.example.umc10th.global.apiPayload;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@JsonPropertyOrder({"isSuccess","timestamp","code","message","result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("timestamp")
    private final String timestamp;

    @JsonProperty("result")
    private T result;

    private ApiResponse(Boolean isSuccess, String code, String message, T result) {
        this.isSuccess = isSuccess;
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.code = code;
        this.message = message;
        this.result = result;
    }

    // [성공]
    public static <T> ApiResponse<T> success(BaseSuccessCode code, T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    // [성공] 필터/핸들러에서 ObjectMapper로 직접 쓸 때 사용 (success의 alias)
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result) {
        return success(code, result);
    }

    // [실패] 에러 핸들러에서 사용 (데이터 없는 일반 에러)
    public static <T> ResponseEntity<ApiResponse<T>> onFailureEntity(BaseErrorCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(onFailureBody(code, null));
    }

    // [실패] 에러 핸들러에서 사용 (데이터 포함 - 예: Validation 에러 메시지)
    public static <T> ResponseEntity<ApiResponse<T>> onFailureEntity(BaseErrorCode code, T result) {
        return ResponseEntity
                .status(code.getStatus())
                .body(onFailureBody(code, result));
    }

    // [실패] 필터에서 ObjectMapper로 직접 쓸 때 사용 (ResponseEntity 없이 body만 필요)
    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T result) {
        return onFailureBody(code, result);
    }

    private static <T> ApiResponse<T> onFailureBody(BaseErrorCode code, T result) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), result);
    }
}
