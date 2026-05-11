package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.Address;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private static final Long CURRENT_MEMBER_ID = 1L;

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;

    // 마이페이지 조회
    public MemberResDTO.MyPage getMyPage() {
        Member member = memberRepository.findById(CURRENT_MEMBER_ID)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));
        return MemberConverter.toMyPageResponse(member);
    }

    // 회원가입
    @Transactional
    public MemberResDTO.SignUp signUp(MemberReqDTO.SignUp dto) {
        Member member = Member.builder()
                .name(dto.name())
                .nickname(dto.nickname())
                .email(dto.email())
                .socialUid(dto.email())
                .socialType(SocialType.KAKAO)
                .gender(parseGender(dto.gender()))
                .birth(dto.birth())
                .address(parseAddress(dto.address()))
                .detailAddress(dto.detailAddress())
                .phoneNumber(dto.phoneNumber())
                .point(0)
                .build();

        Member saved = memberRepository.save(member);
        return MemberConverter.toSignUpResponse(
                saved.getId(),
                saved.getNickname(),
                saved.getEmail()
        );
    }

    // 홈 화면 조회
    public MemberResDTO.Home getHome(Long locationId, Integer page, Integer size) {
        Member member = memberRepository.findById(CURRENT_MEMBER_ID)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));

        long receivedCount = memberMissionRepository.findByMemberIdAndIsComplete(
                CURRENT_MEMBER_ID,
                null,
                PageRequest.of(0, 1)
        ).getTotalElements();

        long completedCount = memberMissionRepository.findByMemberIdAndIsComplete(
                CURRENT_MEMBER_ID,
                true,
                PageRequest.of(0, 1)
        ).getTotalElements();

        long inProgressCount = receivedCount - completedCount;

        int p = page == null ? 0 : page;
        int s = size == null ? 10 : size;

        Page<Mission> missionPage = missionRepository.findByStore_Location_Id(
                locationId,
                PageRequest.of(p, s)
        );

        List<MemberResDTO.ReceivedMission> receivedMissions = missionPage.getContent().stream()
                .map(mission -> new MemberResDTO.ReceivedMission(
                        null,
                        mission.getId(),
                        mission.getStore().getId(),
                        mission.getStore().getName(),
                        mission.getCondition(),
                        mission.getPoint(),
                        "AVAILABLE",
                        mission.getDeadline().atStartOfDay()
                ))
                .toList();

        return new MemberResDTO.Home(
                new MemberResDTO.MemberInfo(
                        member.getId(),
                        member.getNickname(),
                        member.getPoint()
                ),
                new MemberResDTO.MissionSummary(
                        receivedCount,
                        completedCount,
                        inProgressCount
                ),
                receivedMissions
        );
    }

    // Enum 파싱 - Gender
    private Gender parseGender(String value) {
        if (value == null || value.isBlank()) {
            return Gender.NONE;
        }

        try {
            return Gender.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Gender.NONE;
        }
    }

    // Enum 파싱 - Address
    private Address parseAddress(String value) {
        if (value == null || value.isBlank()) {
            return Address.NONE;
        }

        String normalized = value.trim();
        for (Address address : Address.values()) {
            if (address.name().equalsIgnoreCase(normalized) ||
                    address.getDisplayName().equals(normalized)) {
                return address;
            }
        }

        return Address.NONE;
    }
}