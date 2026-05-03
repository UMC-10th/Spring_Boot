package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    SIGN_UP(HttpStatus.OK, "MEMBER200_1", "회원가입에 성공했습니다."),
    GET_HOME(HttpStatus.OK, "MEMBER200_2", "홈 화면 조회에 성공했습니다."),
    GET_MY_PAGE(HttpStatus.OK, "MEMBER200_3", "성공적으로 유저를 조회했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}