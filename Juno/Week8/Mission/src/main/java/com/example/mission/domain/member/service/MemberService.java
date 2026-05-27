package com.example.mission.domain.member.service;

import com.example.mission.domain.member.converter.MemberConverter;
import com.example.mission.domain.member.dto.MemberReqDTO;
import com.example.mission.domain.member.entity.Member;
import com.example.mission.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member joinMember(MemberReqDTO.JoinDTO request) {
        Member newMember = MemberConverter.toMember(request);
        
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        
        // 암호화된 비밀번호로 업데이트 (Member 엔티티가 @AllArgsConstructor, @Builder를 가지고 있으므로 새로 생성하거나 세터를 써야함)
        // 여기선 간단히 새로 생성하는 방식으로 MemberConverter에서 처리하거나 여기서 필드 주입
        // 엔티티에 세터가 없으므로 Reflection이나 Builder를 다시 쓰거나, MemberConverter를 수정해야함.
        // MemberConverter를 수정해서 인코딩된 비번을 받도록 하는게 깔끔할듯.
        
        Member memberWithEncodedPwd = Member.builder()
                .email(newMember.getEmail())
                .password(encodedPassword)
                .name(newMember.getName())
                .gender(newMember.getGender())
                .address(newMember.getAddress())
                .role(newMember.getRole())
                .build();

        return memberRepository.save(memberWithEncodedPwd);
    }
}
