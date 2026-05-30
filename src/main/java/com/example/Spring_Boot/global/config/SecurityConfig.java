package com.example.Spring_Boot.global.config;

import com.example.Spring_Boot.domain.user.security.CustomUserDetailsService;
import com.example.Spring_Boot.domain.user.service.CustomOAuthService;
import com.example.Spring_Boot.global.security.CustomAccessDenied;
import com.example.Spring_Boot.global.security.CustomEntryPoint;
import com.example.Spring_Boot.global.security.OAuthSuccessHandler;
import com.example.Spring_Boot.global.security.filter.JwtAuthFilter;
import com.example.Spring_Boot.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.servlet.HandlerExceptionResolver;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuthService customOAuthService;

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final String[] allowUris = {
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/auth/users",
            "/auth/login",
            "/oauth/login/kakao"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(auth ->
                                auth.baseUri("/oauth/login")
                        )
                        .redirectionEndpoint(redirect ->
                                redirect.baseUri("/oauth/callback/*")
                        )
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuthService)
                        )
                        .successHandler(oAuthSuccessHandler())
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customEntryPoint())
                        .accessDeniedHandler(customAccessDenied())
                )
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, customUserDetailsService);
    }

    @Bean
    public OAuthSuccessHandler oAuthSuccessHandler() {
        return new OAuthSuccessHandler(jwtUtil);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomEntryPoint customEntryPoint() {
        return new CustomEntryPoint(handlerExceptionResolver);
    }

    @Bean
    public CustomAccessDenied customAccessDenied() {
        return new CustomAccessDenied(handlerExceptionResolver);
    }
}