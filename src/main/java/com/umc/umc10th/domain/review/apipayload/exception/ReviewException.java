package com.umc.umc10th.domain.review.apipayload.exception;

import com.umc.umc10th.global.apipayload.code.BaseErrorCode;
import com.umc.umc10th.global.apipayload.exception.ProjectException;

public class ReviewException extends ProjectException {
    ReviewException(BaseErrorCode errorCode) { super(errorCode); }
}