package com.example.mission.domain.mission.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MissionResDTO {

    @Getter
    public static class MissionList {
        Mission[] missionList;
    }

    @Getter
    public static class Mission {
        int point;
    }

    @Builder
    public record GetMission(
            Long missionId,
            Integer point,
            String conditional
    ){}

    // 페이지네이션 틀
    @Builder
    public record Pagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){}
}
