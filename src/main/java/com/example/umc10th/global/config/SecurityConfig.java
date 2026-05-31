package com.example.umc10th.global.config;

import com.example.umc10th.global.security.CustomAccessDenied;
import com.example.umc10th.global.security.CustomEntryPoint;
import com.example.umc10th.global.security.CustomUserDetailsService;
import com.example.umc10th.global.security.filter.JwtAuthFilter;
import com.example.umc10th.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    private final CustomEntryPoint customEntryPoint;
    private final CustomAccessDenied customAccessDenied;

    private final String[] allowUris = {
            // Swagger 허용
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/auth/**",
            "/api/v1/auth/**", // 회원가입 + 로그인 = 인증 없이 허용

            // 폼 로그인 페이지 자체
            "/login",
            "/login/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // 세션 안 씀 (토큰 기반)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customEntryPoint)
                        .accessDeniedHandler(customAccessDenied)
                )
                // 폼 로그인 / 기본 로그인 제거
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 우리 JWT 필터를 인증 필터 앞에 삽입
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, customUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}