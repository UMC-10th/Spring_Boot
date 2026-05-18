package com.example.Spring_Boot.domain.member.exception;

import com.example.Spring_Boot.global.apiPayload.code.BaseErrorCode;
import com.example.Spring_Boot.global.apiPayload.exception.ProjectException;

public class MemberException extends ProjectException {

    public MemberException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
