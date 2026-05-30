package com.example.mission.domain.review.exception;

import com.example.mission.global.apiPayload.code.BaseErrorCode;
import com.example.mission.global.apiPayload.exception.ProjectException;

public class ReviewException extends ProjectException {
    public ReviewException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
