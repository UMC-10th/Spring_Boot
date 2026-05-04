package com.example.umc10th.global.apiPayload.code.status;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GeneralErrorCode implements BaseErrorCode {

    // 도메인과 무관한 공통 에러, 도메인별 에러는 각 도메인 폴더에서 따로 정의 예정
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON405", "지원하지 않는 HTTP 메서드입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "COMMON4001", "요청 값 검증에 실패했습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
