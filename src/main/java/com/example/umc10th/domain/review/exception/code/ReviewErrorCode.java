package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    INVALID_RATING(HttpStatus.BAD_REQUEST, "REVIEW4001", "평점은 1~5 사이여야 합니다."),
    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW4041", "존재하지 않는 가게입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}