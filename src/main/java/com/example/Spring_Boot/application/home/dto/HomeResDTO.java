package com.example.Spring_Boot.application.home.dto;

import com.example.Spring_Boot.domain.mission.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HomeResDTO {

    public record GetHomeResponse(
            Integer point,
            RegionInfo region,
            MissionProgressInfo missionProgress,
            @JsonProperty("MissionList")
            List<MissionInfo> missionList
    ) {
    }

    public record RegionInfo(
            Long regionId,
            String name
    ) {
    }

    public record MissionProgressInfo(
            Integer completedCount
    ) {
    }

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

    public record StoreInfo(
            Long storeId,
            String name
    ) {
    }

    public record CategoryInfo(
            Long categoryId,
            String name
    ) {
    }
}
