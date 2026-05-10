package com.example.Spring_Boot.domain.member.exception.code;

import com.example.Spring_Boot.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    MEMBER_CREATED(
            HttpStatus.CREATED,
            "MEMBER201_1",
            "사용자 생성에 성공했습니다."
    ),
    MEMBER_MY_PAGE_OK(
            HttpStatus.OK,
            "MEMBER200_1",
            "마이페이지 조회에 성공했습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
