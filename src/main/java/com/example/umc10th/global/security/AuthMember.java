package com.example.umc10th.global.security;

import com.example.umc10th.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 시큐리티가 이해할 수 있는 사용자 정보 형식(UserDetails)으로 Member 엔티티를 감싼 어댑터.
 *
 * - Principal 객체로 SecurityContext에 저장된다.
 * - 컨트롤러에서 @AuthenticationPrincipal AuthMember authMember 로 받아 사용 가능.
 */
@Getter
@RequiredArgsConstructor
public class AuthMember implements UserDetails {

    private final Member member;  // 도메인 객체는 안쪽에 숨기고, 필요한 것만 노출

    // 시큐리티가 권한 체크할 때 호출. 권한 분기 미사용이라 일단 ROLE_USER 하나만 부여.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // BCrypt 해시값이 그대로 반환되어야 한다. PasswordEncoder.matches가 내부에서 비교한다.
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // 폼 로그인의 username 자리에 무엇이 들어가는가 = email, 변경가능
    @Override
    public String getUsername() {
        return member.getEmail();
    }

    // 계정 만료 여부 (미사용 → 항상 true)
    @Override
    public boolean isAccountNonExpired() { return true; }

    // 계정 잠김 여부 (미사용 → 항상 true)
    @Override
    public boolean isAccountNonLocked() { return true; }

    // 비밀번호 만료 여부 (미사용 → 항상 true)
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    // 활성 계정 여부. soft delete된 회원은 false로 만들어야 하지만,
    // findByEmailAndDeletedAtIsNull로 이미 걸러 들어오므로 여기선 true.
    @Override
    public boolean isEnabled() { return true; }

    public Long getId() {
        return member.getId();
    }
}