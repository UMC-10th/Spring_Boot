package com.umc.umc10th.domain.mission.apipayload.exception;

import com.umc.umc10th.global.apipayload.code.BaseErrorCode;
import com.umc.umc10th.global.apipayload.exception.ProjectException;

public class MissionException extends ProjectException {
    MissionException(BaseErrorCode errorCode) { super(errorCode); }
}