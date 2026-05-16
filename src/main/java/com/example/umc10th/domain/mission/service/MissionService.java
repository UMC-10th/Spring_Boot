package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRespository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MssionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRespository;
import com.example.umc10th.global.apiPayload.code.GeneralErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRespository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberRespository memberRepository;

    // 홈 화면
    public MssionResDTO.GetHome getHome(MissionReqDTO.GetHome dto, Pageable pageable) {

        Member member = memberRepository.findById(Math.toIntExact(dto.memberId()))
                .orElseThrow(() -> new RuntimeException(GeneralErrorCode.NOT_FOUND.getMessage()));

        Page<Mission> missionPage = missionRepository.findByStore_Location_Id(
                Math.toIntExact(dto.locationId()), pageable
        );

        return MissionConverter.toGetHome(member, missionPage);
    }

    // 내 미션 목록
    public MssionResDTO.GetMyMissions getMyMissions(MissionReqDTO.GetMyMissions dto, Pageable pageable) {

        Page<MemberMission> memberMissionPage;

        if (dto.isComplete() == null) {
            memberMissionPage = memberMissionRepository.findByMember_Id(dto.memberId(), pageable);
        } else {
            memberMissionPage = memberMissionRepository.findByMember_IdAndIsComplete(
                    dto.memberId(), dto.isComplete(), pageable
            );
        }

        return MissionConverter.toGetMyMissions(memberMissionPage);
    }

    @Transactional
    public MssionResDTO.CompleteMission completeMission(Long missionId) {
        // TODO: 구현 예정
        return null;
    }
}
