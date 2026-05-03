package com.example.umc10th.domain.mission.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class MssionResDTO {

    // 홈 화면 응답
    @Builder
    public record GetHome(
            String memberName,
            Integer point,
            List<MissionPreview> missions
    ) {}

    // 개인 미션 정보 응답
    @Builder
    public record MissionPreview(
            Long memberMissionId,
            Long missionId,
            String storeName,
            String conditional,
            Integer point,
            LocalDate deadline,
            Boolean isComplete
    ) {}

    // 내 미션 목록 응답
    @Builder
    public record GetMyMissions(
            List<MissionPreview> missions,
            Integer totalCount
    ) {}

    // 미션 완료 처리 응답
    @Builder
    public record CompleteMission(
            Long memberMissionId,
            Long missionId,
            Boolean isComplete      // 완료 시 true
    ) {}
}

