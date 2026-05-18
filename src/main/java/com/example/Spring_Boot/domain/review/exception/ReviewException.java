package com.example.Spring_Boot.domain.review.exception;

import com.example.Spring_Boot.global.apiPayload.code.BaseErrorCode;
import com.example.Spring_Boot.global.apiPayload.exception.ProjectException;

public class ReviewException extends ProjectException {

    public ReviewException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
