package com.example.mission.domain.member.controller;

import com.example.mission.domain.member.converter.MemberConverter;
import com.example.mission.domain.member.dto.MemberReqDTO;
import com.example.mission.domain.member.dto.MemberResDTO;
import com.example.mission.domain.member.entity.Member;
import com.example.mission.domain.member.service.MemberService;
import com.example.mission.global.apiPayload.ApiResponse;
import com.example.mission.global.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "회원 가입 API", description = "회원 가입을 위한 API이며, email, password 등을 입력받습니다.")
    public ApiResponse<MemberResDTO.JoinResultDTO> join(@RequestBody MemberReqDTO.JoinDTO request) {
        Member member = memberService.joinMember(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MemberConverter.toJoinResultDTO(member));
    }
}
