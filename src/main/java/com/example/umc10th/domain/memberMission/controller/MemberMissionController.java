package com.example.umc10th.domain.memberMission.controller;

import com.example.umc10th.domain.memberMission.dto.MemberMissionResDTO;
import com.example.umc10th.domain.memberMission.exception.code.MemberMissionSuccessCode;
import com.example.umc10th.domain.memberMission.service.MemberMissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberMissionController {

    private final MemberMissionService memberMissionService;

    @GetMapping("/{memberId}/missions")
    public ApiResponse<MemberMissionResDTO.MyMissionListDTO> getMyMissions(
            @PathVariable Long memberId,
            @RequestParam Boolean isComplete,
            @RequestParam(defaultValue = "0") Integer page
    ) {
        return ApiResponse.onSuccess(
                MemberMissionSuccessCode.MY_MISSION_FOUND,
                memberMissionService.getMyMissions(memberId, isComplete, page)
        );
    }
}