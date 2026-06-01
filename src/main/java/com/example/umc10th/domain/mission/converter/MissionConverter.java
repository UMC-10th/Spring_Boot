package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.store.entity.Store;

public class MissionConverter {

    public static Mission toMission(
            MissionReqDTO.CreateMissionDTO request,
            Store store
    ) {

        return Mission.builder()
                .store(store)
                .title(request.title())
                .description(request.description())
                .build();
    }

    public static MissionResDTO.CreateMissionResultDTO toCreateMissionResultDTO(
            Mission mission
    ) {

        return MissionResDTO.CreateMissionResultDTO.builder()
                .missionId(mission.getId())
                .title(mission.getTitle())
                .description(mission.getDescription())
                .createdAt(mission.getCreatedAt())
                .build();
    }

    public static MissionResDTO.MissionInfoDTO toMissionInfoDTO(
            Mission mission
    ) {

        return MissionResDTO.MissionInfoDTO.builder()
                .missionId(mission.getId())
                .title(mission.getTitle())
                .description(mission.getDescription())
                .build();
    }
}