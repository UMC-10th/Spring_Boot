package com.example.umc10thtest.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MissinoResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MissionPreviewRes {
        private Long missionId;
        private String storeName;
        private String missionSpec;
        private Integer reward;
        private LocalDate deadline;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MissionPreviewListRes {
        private List<MissionPreviewRes> missionList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class StoreMissionRes {
        private Long missionId;
        private Integer point;
        private String conditional;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class StoreMissionListRes {
        private List<StoreMissionRes> missionList;
        private Integer listSize;
    }
}
