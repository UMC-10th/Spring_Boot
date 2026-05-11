package com.example.umc10thtest.domain.member.dto;

import com.example.umc10thtest.domain.mission.enums.MissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MemberResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GetMemberRes {
        private String name;
        private String profileUrl;
        private String email;
        private String phoneNumber;
        private Integer point;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MyPageRes {
        private String name;
        private String email;
        private String profileUrl;
        private Integer point;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MissionPreviewRes {
        private Long missionId;
        private String storeName;
        private String missionSpec;
        private Integer reward;
        private LocalDate deadline;
        private MissionStatus status;
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
}
