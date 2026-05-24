package com.example.umc10th.domain.memberMission.controller;

import com.example.umc10th.domain.memberMission.dto.MemberMissionReqDTO;
import com.example.umc10th.domain.memberMission.dto.MemberMissionResDTO;
import com.example.umc10th.domain.memberMission.exception.code.MemberMissionSuccessCode;
import com.example.umc10th.domain.memberMission.service.MemberMissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberMissionController {

    private final MemberMissionService memberMissionService;

    /** 진행 중인 미션 조회 (오프셋, 회원 ID는 Request Body) */
    @PostMapping("/missions/ongoing")
    public ApiResponse<MemberMissionResDTO.MyMissionListDTO> getOngoingMissions(
            @RequestBody @Valid MemberMissionReqDTO.OngoingMissionsQuery request
    ) {
        return ApiResponse.onSuccess(
                MemberMissionSuccessCode.MY_MISSION_FOUND,
                memberMissionService.getOngoingMissions(request)
        );
    }

    @GetMapping("/{memberId}/missions")
    public ApiResponse<MemberMissionResDTO.MyMissionListDTO> getMyMissions(
            @PathVariable Long memberId,
            @RequestParam Boolean isComplete,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return ApiResponse.onSuccess(
                MemberMissionSuccessCode.MY_MISSION_FOUND,
                memberMissionService.getMyMissions(memberId, isComplete, page, pageSize)
        );
    }
}