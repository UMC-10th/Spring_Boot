package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION4041", "존재하지 않는 미션입니다."),
    ALREADY_PARTICIPATING(HttpStatus.CONFLICT, "MISSION4091", "이미 도전 중인 미션입니다."),
    NOT_PARTICIPATING(HttpStatus.BAD_REQUEST, "MISSION4001", "도전 중이지 않은 미션입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}