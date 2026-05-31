package com.example.umc10th.global.security.util;

import com.example.umc10th.global.security.AuthMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final Duration accessExpiration;

    public JwtUtil(
            @Value("${jwt.token.secretKey}") String secret,
            @Value("${jwt.token.expiration.access}") Long accessExpiration
    ) {
        // 시크릿 문자열을 HMAC-SHA 키 객체로 변환 (최소 32바이트 이상이어야 함)
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = Duration.ofMillis(accessExpiration);
    }

    // AccessToken 생성 - 인증된 사용자 정보로 토큰을 만든다
    public String createAccessToken(AuthMember member) {
        return createToken(member, accessExpiration);
    }

    // 토큰에서 이메일(subject) 추출. 파싱 실패 시 null
    public String getEmail(String token) {
        try {
            return getClaims(token).getPayload().getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    // 토큰 유효성 검증 (서명·만료 모두 확인). 유효하면 true
    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // 토큰 생성 내부 로직
    private String createToken(AuthMember member, Duration expiration) {
        Instant now = Instant.now();

        // 권한 정보를 콤마로 join (ROLE_USER 등)
        String authorities = member.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(member.getUsername())          // 이메일을 subject로
                .claim("role", authorities)             // 권한
                .claim("email", member.getUsername())   // 이메일 (편의용)
                .issuedAt(Date.from(now))               // 발급 시각
                .expiration(Date.from(now.plus(expiration)))  // 만료 시각
                .signWith(secretKey)                    // 서명
                .compact();
    }

    // 토큰 파싱 + 서명/만료 검증 → Claims 반환 (실패 시 JwtException)
    private Jws<Claims> getClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .clockSkewSeconds(60)   // 서버 간 시계 오차 60초 허용
                .build()
                .parseSignedClaims(token);
    }
}