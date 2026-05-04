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
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
