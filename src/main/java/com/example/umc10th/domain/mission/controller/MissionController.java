package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mission", description = "미션 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    @Operation(summary = "미션 진행률 조회")
    @GetMapping("/progress")
    public ApiResponse<MissionResDTO.Progress> getProgress(
            @RequestHeader("Authorization") String token
    ) {
        Long userId = 1L;
        MissionResDTO.Progress result = missionService.getProgress(userId);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_PROGRESS_OK, result);
    }

    @Operation(summary = "홈 미션 목록 조회")
    @GetMapping
    public ApiResponse<MissionResDTO.MissionList> getMissions(
            @RequestHeader("Authorization") String token,
            @RequestParam Long areaId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        MissionResDTO.MissionList result = missionService.getMissions(areaId, page, size);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_OK, result);
    }

    @Operation(summary = "미션 도전하기")
    @PostMapping("/{missionId}/participate")
    public ApiResponse<MissionResDTO.ParticipateResult> participate(
            @RequestHeader("Authorization") String token,
            @PathVariable Long missionId
    ) {
        Long userId = 1L;
        MissionResDTO.ParticipateResult result = missionService.participate(userId, missionId);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_PARTICIPATE_OK, result);
    }

    @Operation(summary = "미션 성공 처리")
    @PatchMapping("/{missionId}/complete")
    public ApiResponse<MissionResDTO.CompleteResult> complete(
            @RequestHeader("Authorization") String token,
            @PathVariable Long missionId
    ) {
        Long userId = 1L;
        MissionResDTO.CompleteResult result = missionService.complete(userId, missionId);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_COMPLETE_OK, result);
    }
}