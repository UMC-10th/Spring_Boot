package com.example.Spring_Boot.domain.mission.exception.code;

import com.example.Spring_Boot.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404_1", "미션을 찾을 수 없습니다."),
    QUERY_NOT_VALID(HttpStatus.BAD_REQUEST, "MISSION400_1", "유효하지 않은 query 값입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}