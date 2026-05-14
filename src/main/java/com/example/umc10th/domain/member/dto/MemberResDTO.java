package com.example.umc10th.domain.member.dto;

import com.example.umc10th.global.dto.PageInfoDTO;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class MemberResDTO {

    // 회원가입 응답
    @Builder
    public record SignUp(
            Long memberId,
            String nickname,
            String email
    ) {}

    // 홈 화면 - 회원 정보
    @Builder
    public record HomeMember(
            Long memberId,
            String nickname,
            Integer point
    ) {}

    // 홈 화면 - 미션 요약
    @Builder
    public record MissionSummary(
            Integer receivedCount,
            Integer completedCount,
            Integer inProgressCount
    ) {}

    // 홈 화면 - 받은 미션 정보
    @Builder
    public record ReceivedMission(
            Long memberMissionId,
            Long missionId,
            Long storeId,
            String storeName,
            String condition,
            Integer rewardPoint,
            String status,         // "IN_PROGRESS", "COMPLETED"
            LocalDate deadline
    ) {}

    // 홈 화면 응답 (최종)
    @Builder
    public record Home(
            HomeMember member,
            MissionSummary missionSummary,
            List<ReceivedMission> receivedMissions,
            PageInfoDTO pageInfo
    ) {}

    // 마이페이지 응답
    @Builder
    public record MyPage(
            Long memberId,
            String nickname,
            String email,
            String phoneNumber,
            Integer point,
            String profileUrl
    ) {}
}
