package com.example.umc10thtest.domain.mission.controller;

import com.example.umc10thtest.domain.mission.converter.MissionConverter;
import com.example.umc10thtest.domain.mission.dto.MissinoResDTO;
import com.example.umc10thtest.domain.mission.entity.Mission;
import com.example.umc10thtest.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10thtest.domain.mission.service.MissionService;
import com.example.umc10thtest.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/missions")
@RequiredArgsConstructor
@Tag(name = "Mission", description = "미션 관련 API")
public class MisisonController {

    private final MissionService missionService;

    @GetMapping
    @Operation(summary = "홈 화면 미션 목록 조회", description = "선택된 지역에서 도전 가능한 미션 목록을 페이징으로 조회합니다.")
    public ApiResponse<MissinoResDTO.MissionPreviewListRes> getAvailableMissions(
            @RequestParam Long locationId,
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "0") Integer page
    ) {
        Page<Mission> missionPage = missionService.getAvailableMissions(locationId, memberId, page);
        return ApiResponse.onSuccess(MissionSuccessCode.GET_MISSIONS, MissionConverter.toMissionPreviewListRes(missionPage));
    }
}
