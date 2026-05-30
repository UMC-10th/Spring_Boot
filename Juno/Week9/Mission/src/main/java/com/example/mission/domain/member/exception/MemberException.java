package com.example.mission.domain.member.exception;

import com.example.mission.global.apiPayload.code.BaseErrorCode;
import com.example.mission.global.apiPayload.exception.ProjectException;

public class MemberException extends ProjectException {
    public MemberException(BaseErrorCode code) { super(code); }
}
