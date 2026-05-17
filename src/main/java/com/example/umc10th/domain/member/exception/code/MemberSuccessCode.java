package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    // 마이페이지 조회 성공
    MEMBER_GET_INFO_SUCCESS(HttpStatus.OK,
            "MEMBER200_1",
            "성공적으로 유저를 조회했습니다."
    ),

    // 회원가입 성공
    MEMBER_SIGNUP_SUCCESS(
            HttpStatus.OK,
            "MEMBER200_2",
            "성공적으로 회원가입이 완료되었습니다."
    );


    private final HttpStatus status;
    private final String code;
    private final String message;
}
