package com.example.week5_mission.domain.member.controller;

import com.example.week5_mission.domain.member.dto.MemberReqDTO;
import com.example.week5_mission.domain.member.dto.MemberResDTO;
import com.example.week5_mission.domain.member.service.MemberService;
import com.example.week5_mission.global.apiPayload.ApiResponse;
import com.example.week5_mission.global.apiPayload.code.BaseSuccessCode;
import com.example.week5_mission.global.apiPayload.code.GeneralSuccessCode;
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

}
