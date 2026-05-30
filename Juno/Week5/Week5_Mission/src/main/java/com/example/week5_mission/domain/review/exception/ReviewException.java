package com.example.week5_mission.domain.review.exception;

import com.example.week5_mission.global.apiPayload.code.BaseErrorCode;
import com.example.week5_mission.global.apiPayload.exception.ProjectException;

public class ReviewException extends ProjectException {
    public ReviewException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
