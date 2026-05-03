package com.example.umc10th.global.apiPayload.code.status;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK,"COMMON200","성공"),
    CREATED(HttpStatus.CREATED,"COMMON201","생성됨");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
