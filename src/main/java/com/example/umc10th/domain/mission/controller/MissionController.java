package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MissionController {

    // private final MissionService missionService; 6주차에 활성화하기!

    // 4. 미션 목록 조회 (진행중 / 진행완료 통합)
    @GetMapping("/member-missions")
    public ApiResponse<MissionResDTO.MissionList> getMemberMissions(
            @RequestParam String status,         // "IN_PROGRESS" or "COMPLETED"
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        // 6주차에서 missionService.getMemberMissions(status, page, size)로 교체
        List<MissionResDTO.MissionInfo> missions = List.of(
                MissionResDTO.MissionInfo.builder()
                        .memberMissionId(101L)
                        .missionId(10L)
                        .storeId(3L)
                        .storeName("현안국밥")
                        .condition("10,000원 이상 주문 시")
                        .rewardPoint(500)
                        .status("IN_PROGRESS")
                        .deadline(LocalDate.of(2026, 3, 31))
                        .completedAt(null)
                        .build()
        );

        MissionResDTO.PageInfo pageInfo = MissionResDTO.PageInfo.builder()
                .page(page)
                .size(size)
                .totalElements(24L)
                .totalPages(3)
                .build();

        MissionResDTO.MissionList result = MissionResDTO.MissionList.builder()
                .missions(missions)
                .pageInfo(pageInfo)
                .build();

        return ApiResponse.onSuccess(MissionSuccessCode.GET_MISSION_LIST, result);
    }

    // 5. 미션 성공 누르기
    @PatchMapping("/member-missions/{memberMissionId}/complete")
    public ApiResponse<MissionResDTO.CompleteMission> completeMission(
            @PathVariable Long memberMissionId,
            @RequestBody MissionReqDTO.CompleteMission request
    ) {
        // 6주차에서 missionService.completeMission(memberMissionId, request)로 교체
        MissionResDTO.CompleteMission result = MissionResDTO.CompleteMission.builder()
                .memberMissionId(memberMissionId)
                .status("COMPLETED")
                .earnedPoint(500)
                .currentPoint(3000)
                .completedAt(LocalDateTime.now())
                .build();

        return ApiResponse.onSuccess(MissionSuccessCode.COMPLETE_MISSION, result);
    }
}
