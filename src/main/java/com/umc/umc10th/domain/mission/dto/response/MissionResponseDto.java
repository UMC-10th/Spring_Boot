package com.umc.umc10th.domain.mission.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class MissionResponseDto {
    @Builder
    public record GetMissions (
            List<GetMission> missions
    ){
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
}
