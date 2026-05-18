package com.example.Spring_Boot.application.home.dto;

import com.example.Spring_Boot.domain.mission.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

public class HomeResDTO {

    @Builder
    public record GetHomeResponse(
            Integer point,
            RegionInfo region,
            MissionProgressInfo missionProgress,
            @JsonProperty("MissionList")
            List<MissionInfo> missionList,
            PageInfo pageInfo
    ) {
    }

    @Builder
    public record RegionInfo(
            Long regionId,
            String name
    ) {
    }

    @Builder
    public record MissionProgressInfo(
            Integer completedCount
    ) {
    }

    @Builder
    public record MissionInfo(
            Long userMissionId,
            Status status,
            Long missionId,
            String missionContent,
            Integer compensation,
            StoreInfo store,
            CategoryInfo category
    ) {
    }

    @Builder
    public record StoreInfo(
            Long storeId,
            String name
    ) {
    }

    @Builder
    public record CategoryInfo(
            Long categoryId,
            String name
    ) {
    }

    @Builder
    public record PageInfo(
            Integer page,
            Integer size,
            Long totalElements,
            Integer totalPages,
            Boolean hasNext
    ) {
    }
}
