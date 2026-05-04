package com.umc.umc10th.domain.mission.apipayload.code;

import com.umc.umc10th.global.apipayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404_1", "해당 미션을 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
