package com.example.umc10th.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    String getCode();        // ex) MEMBER400_1
    String getMessage();     // ex) 존재하지 않는 회원입니다.
    HttpStatus getStatus();  // ex) 400 BAD_REQUEST
}
