package com.example.umc10th.global.security;

import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 시큐리티 필터 단에서 발생한 예외를 ApiResponse 형식의 JSON으로 응답하는 공통 유틸.
 *
 * - CustomEntryPoint(401), CustomAccessDenied(403)에서 공통으로 사용
 * - GeneralExceptionAdvice는 필터 예외에 손이 닿지 않으므로 여기서 직접 직렬화
 */
public class SecurityResponseWriter {

    private SecurityResponseWriter() {}

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeErrorResponse(HttpServletResponse response, BaseErrorCode code)
            throws IOException {
        // 1. 응답 Content-Type, HTTP 상태코드 설정
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());

        // 2. ApiResponse 형식으로 본문 객체 생성
        ApiResponse<Void> errorResponse = ApiResponse.onFailure(code, null);

        // 3. JSON 직렬화 후 응답 OutputStream에 쓰기
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}