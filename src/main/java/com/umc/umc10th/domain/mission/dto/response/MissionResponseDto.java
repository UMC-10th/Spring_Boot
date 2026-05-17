package com.umc.umc10th.domain.mission.dto.response;

import lombok.Builder;

import java.util.List;

public class MissionResponseDto {
    @Builder
    public record GetMissions (
            List<GetMission> missions
    ){
        @Builder
        public record GetMission (
            String title,
            String content,
            int reward
        ){}
    }

    @Builder
    public record CountMissions(
            int count
    ){}

    @Builder
    public record GetMissionsPaged(
            List<GetMission> missions,
            Integer pageNumber,
            Integer pageSize,
            Integer totalPages,
            Long totalElements,
            Boolean first,
            Boolean last
    ) {
        @Builder
        public record GetMission(
                Long missionId,
                String storeName,
                String title,
                String content,
                Integer reward,
                String status
        ) {}
    }
}
