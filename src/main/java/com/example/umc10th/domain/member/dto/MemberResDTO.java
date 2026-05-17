package com.example.umc10th.domain.member.dto;

import java.time.LocalDate;
import java.util.List;

public class MemberResDTO {

    // 1) 회원가입 응답
    public record SignUp(
            Long memberId,
            String nickname,
            String email
    ) {
    }

    // 2) 마이페이지 응답
    public record MyPage(
            Long memberId,
            String nickname,
            String email,
            String phoneNumber,
            Integer point,
            String profileUrl
    ) {
    }

    // 3) 홈 화면 - 회원 정보
    public record MemberInfo(
            Long memberId,
            String nickname,
            Integer point
    ) {
    }

    // 4) 홈 화면 - 미션 요약
    public record MissionSummary(
            Long receivedCount,
            Long completedCount,
            Long inProgressCount
    ) {
    }

    // 5) 홈 화면 - 받은 미션 정보
    public record ReceivedMission(
            Long memberMissionId,
            Long missionId,
            Long storeId,
            String storeName,
            String condition,
            Integer rewardPoint,
            String status,
            LocalDate deadline
    ) {
    }

    // 6) 홈 화면 응답
    public record Home(
            MemberInfo member,
            MissionSummary missionSummary,
            List<ReceivedMission> receivedMissions
    ) {
    }
}