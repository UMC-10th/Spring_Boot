package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4041", "존재하지 않는 회원입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "MEMBER4091", "이미 가입된 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER4011", "비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}