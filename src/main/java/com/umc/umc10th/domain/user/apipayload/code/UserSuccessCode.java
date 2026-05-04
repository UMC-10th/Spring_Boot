package com.umc.umc10th.domain.user.apipayload.code;


import com.umc.umc10th.global.apipayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserSuccessCode implements BaseSuccessCode {
    OK(HttpStatus.OK, "USER200_1", "성공적으로 유저를 조회했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
