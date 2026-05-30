package com.example.mission.domain.store.exception;

import com.example.mission.global.apiPayload.code.BaseErrorCode;
import com.example.mission.global.apiPayload.exception.ProjectException;

public class StoreException extends ProjectException {
    public StoreException(BaseErrorCode code) {
        super(code);
    }
}
