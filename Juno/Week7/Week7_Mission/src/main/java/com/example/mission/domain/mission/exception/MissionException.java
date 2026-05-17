package com.example.mission.domain.mission.exception;

import com.example.mission.global.apiPayload.code.BaseErrorCode;
import com.example.mission.global.apiPayload.exception.ProjectException;

public class MissionException extends ProjectException {
    public MissionException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
