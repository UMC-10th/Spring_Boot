package com.umc.umc10th.domain.store.apipayload.exception;

import com.umc.umc10th.global.apipayload.code.BaseErrorCode;
import com.umc.umc10th.global.apipayload.exception.ProjectException;

public class StoreException extends ProjectException {
    StoreException(BaseErrorCode errorCode) { super(errorCode); }
}