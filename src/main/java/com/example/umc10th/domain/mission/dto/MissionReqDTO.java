package com.example.umc10th.domain.mission.dto;

public class MissionReqDTO {

    // 홈 화면 조회 (Security 미적용)
    public record GetHome(
            Long memberId,
            Long locationId
    ) {}

    // 내 미션 목록 조회
    public record GetMyMissions(
            Long memberId,
            Boolean isComplete
    ) {}
}

