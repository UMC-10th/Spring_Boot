package com.example.umc10th.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class MemberResDTO {

    @Builder
    @Getter
    public static class JoinResult {
        private Long memberId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    public static class LoginResult {
        private String accessToken;
        private String tokenType;
    }

    @Builder
    @Getter
    public static class MyPage {
        private Long memberId;
        private String name;
        private String email;
        private Integer point;
        private Integer reviewCount;
    }

    @Builder
    @Getter
    public static class MissionListItem {
        private Long missionId;
        private String title;
        private Integer pointReward;
        private String missionStatus;
        private String restaurantName;
        private String description;
    }

    @Builder
    @Getter
    public static class MissionList {
        private List<MissionListItem> missions;
        private Long totalCount;
        private Integer currentPage;
    }
}