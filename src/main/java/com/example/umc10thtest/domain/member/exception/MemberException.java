package com.example.umc10thtest.domain.member.exception;

import com.example.umc10thtest.global.apiPayload.code.BaseErrorCode;
import com.example.umc10thtest.global.apiPayload.exception.ProjectException;

public class MemberException extends ProjectException {

    public MemberException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
