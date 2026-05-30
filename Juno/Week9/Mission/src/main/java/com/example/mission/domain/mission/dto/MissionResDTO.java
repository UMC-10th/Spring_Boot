package com.example.mission.domain.mission.dto;

import com.example.mission.domain.mission.enums.MissionStatus;
import lombok.Builder;

import java.util.List;

public class MissionResDTO {

    // 가게 내 미션 조회
    @Builder
    public record GetMission(
            Long missionId,
            Integer point,
            String conditional
    ){}

    @Builder
    public record GetMemberMission(
            Long memberMissionId,
            MissionStatus status,
            GetMission mission
    ){}

    // 페이지네이션 틀: 커서 기반
    @Builder
    public record Pagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){}
}
