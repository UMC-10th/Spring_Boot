package com.example.Spring_Boot.domain.mission.converter;

import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.mission.entity.Mission;
import com.example.Spring_Boot.domain.mission.entity.UserMission;

public class MissionConverter {

    public static MissionResDTO.MissionItemDTO toMissionItemDTO(UserMission userMission) {
        Mission mission = userMission.getMission();
        return MissionResDTO.MissionItemDTO.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .conditional(mission.getConditional())
                .point(mission.getReward())
                .status(userMission.getStatus().name())
                .build();
    }

    public static MissionResDTO.MissionItemDTO toHomeMissionItemDTO(Mission mission) {
        return MissionResDTO.MissionItemDTO.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .conditional(mission.getConditional())
                .point(mission.getReward())
                .status("AVAILABLE")
                .build();
    }
}