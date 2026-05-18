package com.example.Spring_Boot.domain.mission.exception;

import com.example.Spring_Boot.global.apiPayload.code.BaseErrorCode;
import com.example.Spring_Boot.global.apiPayload.exception.ProjectException;

public class MissionException extends ProjectException {

    public MissionException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
