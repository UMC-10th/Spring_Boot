package com.example.Spring_Boot.global.security.oauth;

import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import com.example.Spring_Boot.global.security.auth.AuthMember;
import com.example.Spring_Boot.global.security.dto.AuthResDTO;
import com.example.Spring_Boot.global.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuthMember oAuthMember = (OAuthMember) authentication.getPrincipal();
        String accessToken = jwtUtil.createAccessToken(new AuthMember(oAuthMember.getMember()));

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(GeneralSuccessCode.OK.getStatus().value());

        ApiResponse<AuthResDTO.LoginResponse> responseBody = ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                AuthResDTO.LoginResponse.builder()
                        .accessToken(accessToken)
                        .build()
        );

        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }
}
