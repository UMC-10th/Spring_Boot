package com.example.umc10th.domain.member.exception.code;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK,
            "MEMBER200_1",
            "성공적으로 유저를 조회했습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}