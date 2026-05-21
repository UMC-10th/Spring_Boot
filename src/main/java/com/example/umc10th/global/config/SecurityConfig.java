package com.example.umc10th.global.config;

import com.example.umc10th.global.security.CustomAccessDenied;
import com.example.umc10th.global.security.CustomEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ====================================================================
    // Public URI 목록 - 로그인 없이 접근 가능
    // ====================================================================
    private static final String[] PUBLIC_URIS = {
            // 회원가입 (public)
            "/api/members/join",

            // 로그인 (자체 로그인 API + 시큐리티 폼 로그인 진입점)
            "/api/members/login",
            "/login",

            // Swagger 
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",

            // 정적 리소스
            "/", "/index.html", "/favicon.ico",

            // H2 콘솔 
            "/h2-console/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (JWT 기반 API → 쿠키 자동 전송 없음 → CSRF 공격 불가)
                .csrf(csrf -> csrf.disable())

                // H2 콘솔 화면이 iframe 내부에서 렌더링되므로 SAMEORIGIN 허용
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()))

                // 세션 정책 - 폼 로그인은 세션을 쓰지만,
                //    9주차 JWT 적용 시 STATELESS로 바꿀 예정. 일단 IF_REQUIRED 유지.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // URL별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URIS).permitAll()
                        .anyRequest().authenticated()
                )

                // 폼 로그인 활성화 (JWT로 교체 예정)
                .formLogin(form -> form
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                        .permitAll()
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )

                // 인증/인가 실패 시 응답 통일 핸들러 등록
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                );

        return http.build();
    }

    // ====================================================================
    // 빈 등록
    // ====================================================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt: 솔트 자동 생성 + 해시값에 솔트 포함 → 별도 솔트 컬럼 불필요
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDenied();
    }
}