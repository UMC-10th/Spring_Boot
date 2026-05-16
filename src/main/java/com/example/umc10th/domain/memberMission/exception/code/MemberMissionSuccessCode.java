package com.example.umc10th.domain.memberMission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberMissionSuccessCode implements BaseSuccessCode {

    MY_MISSION_FOUND(
            HttpStatus.OK,
            "MEMBER_MISSION200_1",
            "내 미션 목록을 성공적으로 조회했습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
