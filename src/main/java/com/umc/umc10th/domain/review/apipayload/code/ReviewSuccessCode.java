package com.umc.umc10th.domain.review.apipayload.code;


import com.umc.umc10th.global.apipayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {
    OK(HttpStatus.OK, "REVIEW200_1", "성공적으로 리뷰를 조회했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
