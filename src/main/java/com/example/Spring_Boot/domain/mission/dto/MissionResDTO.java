package com.example.Spring_Boot.domain.mission.dto;

import lombok.Builder;
import java.util.List;

public class MissionResDTO {

    // 내가 진행 중인 미션 조회 (오프셋)
    @Builder
    public record GetMission(
            Long missionId,
            String storeName,
            String conditional,
            Integer point,
            String status
    ) {}

    // 페이지네이션 틀 (오프셋용)
    @Builder
    public record OffsetPagination<T>(
            List<T> data,
            Integer pageNumber,
            Integer pageSize
    ) {}

    // 페이지네이션 틀 (커서용)
    @Builder
    public record Pagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ) {}
}