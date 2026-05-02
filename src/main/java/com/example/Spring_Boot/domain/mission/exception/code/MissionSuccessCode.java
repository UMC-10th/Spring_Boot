package com.example.Spring_Boot.domain.mission.exception.code;

import com.example.Spring_Boot.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    USER_MISSION_LIST_OK(
            HttpStatus.OK,
            "USER_MISSION200_1",
            "유저 미션 목록 조회에 성공했습니다."
    ),
    USER_MISSION_SUCCESS_OK(
            HttpStatus.OK,
            "USER_MISSION_SUCCESS200_2",
            "유저 미션 완료 처리에 성공했습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
