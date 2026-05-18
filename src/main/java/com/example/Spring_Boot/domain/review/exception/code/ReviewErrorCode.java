package com.example.Spring_Boot.domain.review.exception.code;

import com.example.Spring_Boot.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    STORE_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "REVIEW404_1",
            "리뷰를 작성할 가게를 찾을 수 없습니다."
    ),
    MEMBER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "REVIEW404_2",
            "리뷰를 작성할 유저를 찾을 수 없습니다."
    ),
    INVALID_AUTHORIZATION(
            HttpStatus.UNAUTHORIZED,
            "REVIEW401_1",
            "유효하지 않은 인증 정보입니다."
    ),
    INVALID_RATING(
            HttpStatus.BAD_REQUEST,
            "REVIEW400_1",
            "리뷰 평점은 1점 이상 5점 이하로 입력해야 합니다."
    ),
    INVALID_CURSOR_REQUEST(
            HttpStatus.BAD_REQUEST,
            "REVIEW400_2",
            "커서 요청 값이 올바르지 않습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
