package com.example.Spring_Boot.domain.mission.exception.code;

import com.example.Spring_Boot.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    INVALID_AUTHORIZATION(
            HttpStatus.UNAUTHORIZED,
            "USER_MISSION401_1",
            "유효하지 않은 인증 정보입니다."
    ),
    INVALID_PAGE_REQUEST(
            HttpStatus.BAD_REQUEST,
            "USER_MISSION400_1",
            "페이지 번호와 페이지 크기는 올바른 값으로 입력해야 합니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
