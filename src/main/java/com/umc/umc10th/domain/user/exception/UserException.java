package com.umc.umc10th.domain.user.exception;

import com.umc.umc10th.global.apipayload.code.BaseErrorCode;
import com.umc.umc10th.global.apipayload.exception.ProjectException;

public class UserException extends ProjectException {
    public UserException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
