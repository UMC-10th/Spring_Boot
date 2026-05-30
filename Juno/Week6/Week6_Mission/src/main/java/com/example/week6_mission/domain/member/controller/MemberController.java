package com.example.week6_mission.domain.member.controller;

import com.example.week6_mission.domain.member.dto.MemberReqDTO;
import com.example.week6_mission.domain.member.dto.MemberResDTO;
import com.example.week6_mission.domain.member.service.MemberService;
import com.example.week6_mission.domain.mission.dto.MissionResDTO;
import com.example.week6_mission.global.apiPayload.ApiResponse;
import com.example.week6_mission.global.apiPayload.code.BaseSuccessCode;
import com.example.week6_mission.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    // Request Body
    @PostMapping("/signup")
    public ApiResponse<MemberResDTO.SignUpRequestBody> signUp(
            @RequestBody MemberReqDTO.SignUpRequestBody dto
    ){
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.signup(dto));
    }

    
    // 미션 성공
    // Path Variable
    @PostMapping("/{missionId}")
    public ApiResponse<MissionResDTO.Mission> missionSuccess(
            @PathVariable String missionId,
            @RequestHeader("Authorization") String auth
    ){
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.missionSuccess(missionId, auth));
    }
}
