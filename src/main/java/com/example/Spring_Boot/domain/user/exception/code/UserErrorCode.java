package com.example.Spring_Boot.domain.user.exception.code;

import com.example.Spring_Boot.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404_1", "존재하지 않는 회원입니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER409_1", "이미 존재하는 회원입니다."),
    USER_EMAIL_DUPLICATE(HttpStatus.CONFLICT, "USER409_2", "이미 사용 중인 이메일입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}