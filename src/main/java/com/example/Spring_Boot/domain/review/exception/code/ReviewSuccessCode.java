package com.example.Spring_Boot.domain.review.exception.code;

import com.example.Spring_Boot.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {

    REVIEW_CREATED(
            HttpStatus.CREATED,
            "REVIEW201_1",
            "리뷰 작성이 완료되었습니다."
    ),
    MY_REVIEW_LIST_OK(
            HttpStatus.OK,
            "REVIEW200_1",
            "내가 작성한 리뷰 목록 조회에 성공했습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
