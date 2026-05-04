package com.example.umc10thtest.global.apiPayload.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.example.umc10thtest.global.apiPayload.code.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {

    private final BaseErrorCode errorCode;
}