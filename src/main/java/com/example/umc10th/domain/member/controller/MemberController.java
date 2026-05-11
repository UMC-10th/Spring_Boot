package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ApiResponse<MemberResDTO.JoinResultDTO> joinMember(
            @RequestBody MemberReqDTO.JoinDTO request
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                memberService.joinMember(request)
        );
    }

    @GetMapping("/{memberId}")
    public ApiResponse<MemberResDTO.MemberInfoDTO> getMember(
            @PathVariable Long memberId
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                memberService.getMember(memberId)
        );
    }

    @DeleteMapping("/{memberId}")
    public ApiResponse<String> deleteMember(
            @PathVariable Long memberId
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                memberService.deleteMember(memberId)
        );
    }
}