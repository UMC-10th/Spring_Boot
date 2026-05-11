package com.example.umc10thtest.domain.mission.exception.code;

import com.example.umc10thtest.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {
    GET_MISSIONS(HttpStatus.OK, "MISSION200_1", "미션 목록 조회 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
