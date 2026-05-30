package com.example.mission.service.auth;

import com.example.mission.domain.member.entity.Member;
import com.example.mission.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseGet(() -> memberRepository.findBySocialUid(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username)));

        return new AuthMember(member);
    }
}
