package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // final 필드를 파라미터로 받는 생성자를 자동으로 생성해준다. (의존성 주입 편리하게)
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;

    // 마이페이지
    public MemberResDTO.MyPage getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return MemberConverter.toMyPage(member);
    }

    // 홈 화면
    public MemberResDTO.Home getHome(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 미션 카운트 조회
        Long received = memberMissionRepository.countByMemberId(memberId);
        Long completed = memberMissionRepository.countByMemberIdAndStatus(memberId, true);
        Long inProgress = memberMissionRepository.countByMemberIdAndStatus(memberId, false);

        // 받은 미션 목록 조회
        List<MemberMission> missions = memberMissionRepository.findAllByMemberId(memberId);

        return MemberConverter.toHome(member, received, completed, inProgress, missions);
    }
}
