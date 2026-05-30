package com.example.week6_mission.domain.member.exception;

import com.example.week6_mission.global.apiPayload.code.BaseErrorCode;
import com.example.week6_mission.global.apiPayload.exception.ProjectException;

public class MemberException extends ProjectException {
    public MemberException(BaseErrorCode errorCode) { super(errorCode); }
}
