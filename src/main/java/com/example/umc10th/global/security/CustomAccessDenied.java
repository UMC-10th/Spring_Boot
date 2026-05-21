package com.example.umc10th.global.security;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.status.GeneralErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 인증은 됐지만 권한이 부족한 사용자가 자원에 접근했을 때 호출.
 * → 403 Forbidden + ApiResponse 형식 JSON 응답
 */
@Slf4j
public class CustomAccessDenied implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        log.warn("인가 실패: {} - {}", request.getRequestURI(), accessDeniedException.getMessage());
        BaseErrorCode code = GeneralErrorCode.FORBIDDEN;
        SecurityResponseWriter.writeErrorResponse(response, code);
    }
}