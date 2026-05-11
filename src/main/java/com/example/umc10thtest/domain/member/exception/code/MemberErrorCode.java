package com.example.umc10thtest.domain.member.exception.code;

import com.example.umc10thtest.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "해당 유저를 찾을 수 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE404_1", "해당 가게를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
