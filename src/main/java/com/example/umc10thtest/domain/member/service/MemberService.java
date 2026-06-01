package com.example.umc10thtest.domain.member.service;

import com.example.umc10thtest.domain.member.entity.Member;
import com.example.umc10thtest.domain.member.exception.MemberException;
import com.example.umc10thtest.domain.member.exception.code.MemberErrorCode;
import com.example.umc10thtest.domain.member.repository.MemberRepository;
import com.example.umc10thtest.domain.mission.entity.mapping.MemberMission;
import com.example.umc10thtest.domain.mission.enums.MissionStatus;
import com.example.umc10thtest.domain.mission.repository.MemberMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public Page<MemberMission> getMyMissions(Long memberId, MissionStatus status, Integer page) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return memberMissionRepository.findByMemberAndStatus(member, status, PageRequest.of(page, 10));
    }
}
