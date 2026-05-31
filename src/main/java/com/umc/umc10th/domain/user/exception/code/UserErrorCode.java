package com.umc.umc10th.domain.user.exception.code;

import com.umc.umc10th.global.apipayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USER409_1", "이미 사용 중인 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404_1", "존재하지 않는 유저입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER401_1", "이메일 또는 비밀번호가 올바르지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }
}
