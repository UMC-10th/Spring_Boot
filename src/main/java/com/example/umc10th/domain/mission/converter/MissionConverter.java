package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.global.dto.PageInfoDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {

    // 미션 단건 정보
    public static MissionResDTO.MissionInfo toMissionInfo(MemberMission mm) {
        return MissionResDTO.MissionInfo.builder()
                .memberMissionId(mm.getId())
                .missionId(mm.getMission().getId())
                .storeId(mm.getMission().getStore().getId())
                .storeName(mm.getMission().getStore().getName())
                .condition(mm.getMission().getConditional())
                .rewardPoint(mm.getMission().getPoint())
                .status(mm.isComplete() ? "COMPLETED" : "IN_PROGRESS")
                .deadline(mm.getMission().getDeadline())
                .completedAt(mm.isComplete() ? mm.getUpdatedAt() : null)
                .build();
    }

    // 전체 응답(미션 목록 응답) (페이징 포함!)
    public static MissionResDTO.MissionList toMissionList(Page<MemberMission> page) {
        List<MissionResDTO.MissionInfo> missions = page.getContent().stream()
                .map(MissionConverter::toMissionInfo)
                .toList();

        return MissionResDTO.MissionList.builder()
                .missions(missions)
                .pageInfo(PageInfoDTO.from(page))
                .build();
    }
}
