package com.example.umc10th.domain.memberMission.service;

import com.example.umc10th.domain.memberMission.dto.MemberMissionResDTO;
import com.example.umc10th.domain.memberMission.entity.MemberMission;
import com.example.umc10th.domain.memberMission.repository.MemberMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMissionService {

    private final MemberMissionRepository memberMissionRepository;

    public MemberMissionResDTO.MyMissionListDTO getMyMissions(
            Long memberId,
            Boolean isComplete,
            Integer page
    ) {
        Page<MemberMission> memberMissionPage =
                memberMissionRepository.findMyMissions(
                        memberId,
                        isComplete,
                        PageRequest.of(page, 10)
                );

        List<MemberMissionResDTO.MyMissionDTO> missionList =
                memberMissionPage.stream()
                        .map(memberMission -> MemberMissionResDTO.MyMissionDTO.builder()
                                .memberMissionId(memberMission.getId())
                                .missionId(memberMission.getMission().getId())
                                .storeName(memberMission.getMission().getStore().getName())
                                .missionTitle(memberMission.getMission().getTitle())
                                .missionDescription(memberMission.getMission().getDescription())
                                .isComplete(memberMission.getIsComplete())
                                .completedAt(memberMission.getCompletedAt())
                                .build())
                        .toList();

        return MemberMissionResDTO.MyMissionListDTO.builder()
                .missionList(missionList)
                .listSize(missionList.size())
                .totalPage(memberMissionPage.getTotalPages())
                .totalElements(memberMissionPage.getTotalElements())
                .isFirst(memberMissionPage.isFirst())
                .isLast(memberMissionPage.isLast())
                .build();
    }
}