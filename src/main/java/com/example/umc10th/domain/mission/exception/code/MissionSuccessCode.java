package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    MISSION_CREATED(
            HttpStatus.CREATED,
            "MISSION201_1",
            "미션이 성공적으로 생성되었습니다."
    ),
    MISSION_FOUND(
            HttpStatus.OK,
            "MISSION200_1",
            "성공적으로 미션을 조회했습니다."
    ),
    HOME_MISSION_FOUND(
            HttpStatus.OK,
            "MISSION200_2",
            "홈 미션 목록을 성공적으로 조회했습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
