package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    INVALID_SORT(
            HttpStatus.BAD_REQUEST,
            "REVIEW400_1",
            "정렬(sort)은 id 또는 rating 만 허용됩니다."
    ),
    INVALID_CURSOR(
            HttpStatus.BAD_REQUEST,
            "REVIEW400_2",
            "커서 형식이 올바르지 않습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
