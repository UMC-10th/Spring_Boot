package com.example.Spring_Boot.domain.mission.dto;

import com.example.Spring_Boot.domain.mission.enums.Status;
import com.example.Spring_Boot.domain.store.entity.Category;
import com.example.Spring_Boot.domain.store.entity.Store;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MissionResDTO {

    public record MissionListResponse(
            @JsonProperty("missionList")
            List<UserMissionInfo> missionList
    ) {
    }

    public record UserMissionInfo(
            Long userMissionId,
            Status status,
            Long missionId,
            String missionContent,
            Integer compensation,
            Store storeInfo,
            Category categoryInfo
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

    public record MissionSuccessResponse(
            Long userMissionId,
            Status status
    ) {
    }
}
