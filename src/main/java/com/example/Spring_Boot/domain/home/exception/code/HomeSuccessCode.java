package com.example.Spring_Boot.domain.home.exception.code;

import com.example.Spring_Boot.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HomeSuccessCode implements BaseSuccessCode {

    HOME_GET_OK(
            HttpStatus.OK,
            "HOME200_1",
            "홈 화면 조회에 성공했습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
