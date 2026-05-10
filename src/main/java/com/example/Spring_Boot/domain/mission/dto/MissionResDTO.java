package com.example.Spring_Boot.domain.mission.dto;

import com.example.Spring_Boot.domain.mission.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

public class MissionResDTO {

    @Builder
    public record MissionListResponse(
            @JsonProperty("missionList")
            List<UserMissionInfo> missionList,
            PageInfo pageInfo
    ) {
    }

    @Builder
    public record UserMissionInfo(
            Long userMissionId,
            Status status,
            Long missionId,
            String missionContent,
            Integer compensation,
            StoreInfo storeInfo,
            CategoryInfo categoryInfo
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
    public record MissionSuccessResponse(
            Long userMissionId,
            Status status
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
