package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {

    GET_STORE_INFO(HttpStatus.OK, "REVIEW200_1", "가게 정보 조회에 성공했습니다."),
    CREATE_REVIEW(HttpStatus.OK, "REVIEW200_2", "리뷰 작성에 성공했습니다."),
    GET_MY_REVIEWS(HttpStatus.OK, "REVIEW200_3", "내가 작성한 리뷰 조회에 성공했습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
