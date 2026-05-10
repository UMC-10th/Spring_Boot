package com.example.week6_mission.domain.store.exception;

import com.example.week6_mission.global.apiPayload.code.BaseErrorCode;
import com.example.week6_mission.global.apiPayload.exception.ProjectException;

public class StoreException extends ProjectException {
    public StoreException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
