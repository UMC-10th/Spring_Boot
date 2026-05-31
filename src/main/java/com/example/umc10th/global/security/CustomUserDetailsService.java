package com.example.umc10th.global.security;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 시큐리티가 인증 시도 시 호출하는 사용자 조회 서비스.
 *
 * AuthenticationProvider(DaoAuthenticationProvider)
 *   → UserDetailsService.loadUserByUsername(email)
 *   → 반환된 UserDetails의 password와 입력 password를 PasswordEncoder.matches로 비교
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "해당 이메일로 가입된 회원이 없습니다: " + email
                ));
        return new AuthMember(member);
    }
}