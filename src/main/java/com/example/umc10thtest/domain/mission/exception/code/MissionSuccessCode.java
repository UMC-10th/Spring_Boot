package com.example.umc10thtest.domain.mission.exception.code;

import com.example.umc10thtest.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {
    CREATE_MISSION(HttpStatus.OK, "MISSION200_1", "성공적으로 미션을 생성했습니다."),
    GET_STORE_MISSIONS(HttpStatus.OK, "MISSION200_2", "성공적으로 미션을 조회했습니다."),
    GET_MISSIONS(HttpStatus.OK, "MISSION200_3", "미션 목록 조회 성공"),
    GET_MY_PROGRESSING_MISSIONS(HttpStatus.OK, "MISSION200_4", "진행중인 미션 목록 조회 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
