package com.example.Spring_Boot.domain.mission.converter;

import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.mission.entity.Mission;
import com.example.Spring_Boot.domain.mission.entity.UserMission;
import java.util.List;

public class MissionConverter {

    // UserMission → GetMission DTO
    public static MissionResDTO.GetMission toMissionItemDTO(UserMission userMission) {
        Mission mission = userMission.getMission();
        return MissionResDTO.GetMission.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .conditional(mission.getConditional())
                .point(mission.getReward())
                .status(userMission.getStatus().name())
                .build();
    }

    // Mission → GetMission DTO
    public static MissionResDTO.GetMission toHomeMissionItemDTO(Mission mission) {
        return MissionResDTO.GetMission.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .conditional(mission.getConditional())
                .point(mission.getReward())
                .status("AVAILABLE")
                .build();
    }

    // 오프셋 페이지네이션 틀 생성
    public static <T> MissionResDTO.OffsetPagination<T> toOffsetPagination(
            List<T> data, Integer pageNumber, Integer pageSize) {
        return MissionResDTO.OffsetPagination.<T>builder()
                .data(data)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
    }

    // 커서 페이지네이션 틀 생성
    public static <T> MissionResDTO.Pagination<T> toPagination(
            List<T> data, Boolean hasNext, String nextCursor, Integer pageSize) {
        return MissionResDTO.Pagination.<T>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .pageSize(pageSize)
                .build();
    }
}