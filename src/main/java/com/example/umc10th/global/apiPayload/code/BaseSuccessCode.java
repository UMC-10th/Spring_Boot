package com.example.umc10th.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseSuccessCode {

    String getCode();        // ex) MEMBER200_1
    String getMessage();     // ex) 성공적으로 조회했습니다.
    HttpStatus getStatus();  // ex) 200 OK
}