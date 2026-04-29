package com.example.week5_mission.domain.mission.exception;

import com.example.week5_mission.global.apiPayload.code.BaseErrorCode;
import com.example.week5_mission.global.apiPayload.exception.ProjectException;

public class MissionException extends ProjectException {
    public MissionException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
