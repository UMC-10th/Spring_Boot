package com.example.Spring_Boot.global.security;

import com.example.Spring_Boot.domain.user.converter.UserConverter;
import com.example.Spring_Boot.domain.user.security.AuthMember;
import com.example.Spring_Boot.domain.user.security.OAuthMember;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.BaseSuccessCode;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import com.example.Spring_Boot.global.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BaseSuccessCode code = GeneralSuccessCode.OK;

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());

        OAuthMember member = (OAuthMember) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String accessToken = jwtUtil.createAccessToken(new AuthMember(member.getUser()));

        ApiResponse<Object> responseBody = ApiResponse.onSuccess(
                code,
                UserConverter.toLoginResultDTO(accessToken)
        );

        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}