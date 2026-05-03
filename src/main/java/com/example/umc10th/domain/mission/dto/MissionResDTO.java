package com.example.umc10th.domain.mission.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

    @Builder
    @Getter
    public static class Progress {
        private Integer completedCount;
        private Integer totalCount;
    }

    @Builder
    @Getter
    public static class ListItem {
        private Long missionId;
        private String title;
        private Integer pointReward;
        private String description;
    }

    @Builder
    @Getter
    public static class MissionList {
        private List<ListItem> missions;
        private Long totalCount;
        private Integer currentPage;
    }

    @Builder
    @Getter
    public static class ParticipateResult {
        private Long participationId;
        private String missionStatus;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    public static class CompleteResult {
        private Long participationId;
        private String missionStatus;
        private LocalDateTime completedAt;
        private Integer pointReward;
    }
}