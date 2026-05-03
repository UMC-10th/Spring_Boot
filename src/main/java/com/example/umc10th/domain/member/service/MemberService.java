package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public String singleParameter(String singleParameter) {
        return singleParameter;
    }

    public MemberResDTO.RequestBody requestBody(
            MemberReqDTO.RequestBody dto
    ) {
        return MemberConverter.toRequestBody(
                dto.stringTest(),
                dto.longTest()
        );
    }

    @Transactional
    public String createUser() {
        Member member = Member.builder()
                .name("test")
                .build();

        memberRepository.save(member);
        return "OK";
    }

    @Transactional
    public String deleteUser() {
        memberRepository.deleteByName("test");
        return "OK";
    }
}