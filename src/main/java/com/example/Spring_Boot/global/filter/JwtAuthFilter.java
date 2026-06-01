package com.example.Spring_Boot.global.filter;

import com.example.Spring_Boot.global.security.auth.CustomUserDetailsService;
import com.example.Spring_Boot.global.security.auth.JwtAuthErrorType;
import com.example.Spring_Boot.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            token = token.replace("Bearer ", "");

            Optional<JwtAuthErrorType> authError = jwtUtil.getAuthError(token);
            if (authError.isPresent()) {
                commence(request, response, authError.get());
                return;
            }

            String email = jwtUtil.getEmail(token);
            if (email == null) {
                commence(request, response, JwtAuthErrorType.INVALID_TOKEN);
                return;
            }

            UserDetails user = customUserDetailsService.loadUserByUsername(email);
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (AuthenticationException e) {
            commence(request, response, JwtAuthErrorType.INVALID_TOKEN, e);
            return;
        } catch (Exception e) {
            commence(
                    request,
                    response,
                    JwtAuthErrorType.INVALID_TOKEN,
                    new BadCredentialsException("Invalid JWT token", e)
            );
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            JwtAuthErrorType authErrorType
    ) throws IOException, ServletException {
        AuthenticationException exception = switch (authErrorType) {
            case EXPIRED_TOKEN -> new CredentialsExpiredException("Expired JWT token");
            case INVALID_TOKEN -> new BadCredentialsException("Invalid JWT token");
        };

        commence(request, response, authErrorType, exception);
    }

    private void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            JwtAuthErrorType authErrorType,
            AuthenticationException exception
    ) throws IOException, ServletException {
        request.setAttribute(JwtAuthErrorType.REQUEST_ATTRIBUTE, authErrorType);
        authenticationEntryPoint.commence(request, response, exception);
    }
}
