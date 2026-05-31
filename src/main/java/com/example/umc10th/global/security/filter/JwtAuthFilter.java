package com.example.umc10th.global.security.filter;

import com.example.umc10th.global.security.CustomUserDetailsService;
import com.example.umc10th.global.security.util.JwtUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * 요청마다 한 번 실행되며(OncePerRequestFilter), Authorization 헤더의 JWT를 검사한다.
 * - 토큰 없음/Bearer 아님 → 그냥 다음 필터로 (이후 EntryPoint가 401 처리)
 * - 유효한 토큰 → 인증 객체 생성 후 SecurityContext에 주입
 * - 잘못된 토큰(파싱/서명/만료 실패) → resolver로 예외 토스 → GeneralExceptionAdvice가 통일 응답
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final HandlerExceptionResolver resolver;

    public JwtAuthFilter(JwtUtil jwtUtil,
                         CustomUserDetailsService customUserDetailsService,
                         @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // 토큰이 없거나 Bearer 형식이 아니면 인증 시도 없이 통과
        // (보호된 자원이면 이후 시큐리티가 EntryPoint로 401 처리)
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        try {
            if (jwtUtil.isValid(token)) {
                // 토큰에서 이메일 추출 → 사용자 조회 → 인증 객체 생성
                String email = jwtUtil.getEmail(token);
                UserDetails user = customUserDetailsService.loadUserByUsername(email);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                // SecurityContext에 인증 객체 주입 → 이 요청은 인증된 상태가 됨
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                // 토큰은 있는데 유효하지 않음 → 예외를 토스 (8주차 통일 응답 흐름 재사용)
                throw new BadCredentialsException("유효하지 않은 토큰입니다.");
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // BadCredentialsException 등은 AuthenticationException의 하위라
            // GeneralExceptionAdvice의 handleAuthenticationException(401)이 잡는다.
            SecurityContextHolder.clearContext();
            resolver.resolveException(request, response, null,
                    new BadCredentialsException("토큰 인증에 실패했습니다.", e));
        }
    }
}