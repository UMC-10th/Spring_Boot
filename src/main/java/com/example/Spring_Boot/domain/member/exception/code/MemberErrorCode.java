package com.example.Spring_Boot.domain.member.exception.code;

import com.example.Spring_Boot.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    INVALID_AUTHORIZATION(
            HttpStatus.UNAUTHORIZED,
            "MEMBER401_1",
            "유효하지 않은 인증 정보입니다."
    ),
    MEMBER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "MEMBER404_1",
            "유저를 찾을 수 없습니다."
    ),
    DUPLICATE_EMAIL(
            HttpStatus.CONFLICT,
            "MEMBER409_1",
            "이미 사용 중인 이메일입니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
