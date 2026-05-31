package com.example.umc10th.global.security;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.status.GeneralErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 인증되지 않은 사용자가 보호된 자원에 접근했을 때 호출.
 * → 401 Unauthorized + ApiResponse 형식 JSON 응답
 */
@Slf4j
public class CustomEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        log.warn("인증 실패: {} - {}", request.getRequestURI(), authException.getMessage());
        BaseErrorCode code = GeneralErrorCode.UNAUTHORIZED;
        SecurityResponseWriter.writeErrorResponse(response, code);
    }
}