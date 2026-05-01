package com.example.Spring_Boot.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    HttpStatus getStatus();
    String GetCode();
    String GetMessage();
}
