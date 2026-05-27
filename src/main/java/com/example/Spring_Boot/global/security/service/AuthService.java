package com.example.Spring_Boot.global.security.service;

import com.example.Spring_Boot.global.security.auth.AuthMember;
import com.example.Spring_Boot.global.security.auth.CustomUserDetailsService;
import com.example.Spring_Boot.global.security.dto.AuthReqDTO;
import com.example.Spring_Boot.global.security.dto.AuthResDTO;
import com.example.Spring_Boot.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public AuthResDTO.LoginResponse login(AuthReqDTO.LoginRequest request) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.email());

        if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            throw new BadCredentialsException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtUtil.createAccessToken((AuthMember) userDetails);
        return AuthResDTO.LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
