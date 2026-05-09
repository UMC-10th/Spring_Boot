package com.example.umc10th.domain.memberMission.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class MemberMissionResDTO {

    @Builder
    public record MyMissionDTO(
            Long memberMissionId,
            Long missionId,
            String storeName,
            String missionTitle,
            String missionDescription,
            Boolean isComplete,
            LocalDateTime completedAt
    ) {
    }

    @Builder
    public record MyMissionListDTO(
            List<MyMissionDTO> missionList,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ) {
    }
}