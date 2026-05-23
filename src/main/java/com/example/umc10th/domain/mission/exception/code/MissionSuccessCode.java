package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    HOME_OK(HttpStatus.OK,
            "MISSION200_1",
            "홈 화면 정보를 성공적으로 조회했습니다."),

    MY_MISSIONS_OK(HttpStatus.OK,
            "MISSION200_2",
            "내 미션 목록을 성공적으로 조회했습니다."),

    MISSION_COMPLETE_OK(HttpStatus.OK,
            "MISSION200_3",
            "미션을 성공적으로 완료 처리했습니다."),
    CREATED(HttpStatus.OK,
            "MISSION200_4",
            "성공적으로 미션을 생성했습니다."),
    OK(HttpStatus.OK,
            "MISSION200_5",
            "성공적으로 미션을 조회했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}

