package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRespository;
import com.example.umc10th.global.apiPayload.code.GeneralErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRespository memberRepository;

    public MemberResDTO.GetInfo getInfo(MemberReqDTO.GetInfo dto) {
        // TODO: 구현 예정
        return null;
    }

    public MemberResDTO.GetInfo getMyPage(Long memberId) {

        Member member = memberRepository.findById(Math.toIntExact(memberId))
                .orElseThrow(() -> new RuntimeException(GeneralErrorCode.NOT_FOUND.getMessage()));

        return MemberConverter.toGetInfo(member);
    }

    @Transactional
    public MemberResDTO.SignUp signUp(MemberReqDTO.SignUp dto) {
        // TODO: 구현 예정
        return null;
    }
}
