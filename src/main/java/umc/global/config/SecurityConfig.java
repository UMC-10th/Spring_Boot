package umc.global.config;

import jakarta.servlet.http.HttpServletResponse;
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

import umc.global.config.security.CustomAccessDenied;
import umc.global.config.security.CustomEntryPoint;
import umc.global.security.filter.JwtAuthFilter;
import umc.global.security.handler.OAuthSuccessHandler;
import umc.global.security.service.CustomOAuthService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAccessDenied customAccessDenied;
    private final CustomEntryPoint customEntryPoint;

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomOAuthService customOAuthService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    private final String[] publicApis = {
            "/auth/sign-up",
            "/login",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/oauth/**",
            "/oauth2/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                // 폼 로그인 비활성화 및 세션 STATELESS 설정
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicApis).permitAll()
                        .anyRequest().authenticated()
                )

                // JWT 필터 추가
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // OAuth2 로그인 설정
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(auth -> auth.baseUri("/oauth/authorize"))
                        .redirectionEndpoint(redirect -> redirect.baseUri("/oauth/callback/*"))
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuthService))
                        .successHandler(oAuthSuccessHandler)
                        // 에러 확인을 위한 실패 핸들러 추가
                        .failureHandler((request, response, exception) -> {
                            exception.printStackTrace(); // 콘솔에 빨간 에러 로그 출력
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            response.getWriter().write("{\"error\": \"소셜 로그인 실패\", \"message\": \"" + exception.getMessage() + "\"}");
                        })
                )

                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDenied)
                        .authenticationEntryPoint(customEntryPoint)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}