package com.example.Spring_Boot.domain.user.exception;

import com.example.Spring_Boot.domain.user.exception.code.UserErrorCode;
import com.example.Spring_Boot.global.exception.ProjectException;

public class UserException extends ProjectException {
    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }
}