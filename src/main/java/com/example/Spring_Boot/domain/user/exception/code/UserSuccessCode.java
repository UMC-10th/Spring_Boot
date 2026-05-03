package com.example.Spring_Boot.domain.user.exception.code;

import com.example.Spring_Boot.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserSuccessCode implements BaseSuccessCode {

    USER_FOUND(HttpStatus.OK, "USER200_1", "성공적으로 회원을 조회했습니다."),
    USER_CREATED(HttpStatus.CREATED, "USER201_1", "성공적으로 회원가입이 완료되었습니다."),
    USER_UPDATED(HttpStatus.OK, "USER200_2", "성공적으로 회원 정보가 수정되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}