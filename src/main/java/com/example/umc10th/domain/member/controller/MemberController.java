package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.dto.OffsetPage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.umc10th.global.security.AuthMember;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원가입을 진행한다.")
    @PostMapping("/join")
    public ApiResponse<MemberResDTO.JoinResult> join(
            @Valid @RequestBody MemberReqDTO.Join request) {
        MemberResDTO.JoinResult result = memberService.join(request);
        return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_JOIN_OK, result);
    }

    @Operation(summary = "로그인", description = "이메일/비밀번호로 로그인한다.")
    @PostMapping("/login")
    public ApiResponse<MemberResDTO.LoginResult> login(
            @Valid @RequestBody MemberReqDTO.Login request) {
        MemberResDTO.LoginResult result = memberService.login(request);
        return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_LOGIN_OK, result);
    }

    @Operation(summary = "마이페이지 조회", description = "내 정보를 조회한다.")
    @GetMapping("/my")
    public ApiResponse<MemberResDTO.MyPage> getMyPage(
            @AuthenticationPrincipal AuthMember authMember) {
        MemberResDTO.MyPage result = memberService.getMyPage(authMember.getId());
        return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_FETCH_OK, result);
    }

    // @Operation(summary = "내 미션 목록 조회", description = "진행중/완료 미션을 조회한다.")
    // @GetMapping("/missions")
    // public ApiResponse<MemberResDTO.MissionList> getMyMissions(
    // @RequestHeader("Authorization") String token,
    // @RequestParam(defaultValue = "IN_PROGRESS") String status,
    // @RequestParam(defaultValue = "0") Integer page,
    // @RequestParam(defaultValue = "10") Integer size
    // ) {
    // Long userId = 1L;
    // MemberResDTO.MissionList result = memberService.getMyMissions(userId, status,
    // page, size);
    // return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_MISSION_LIST_OK,
    // result);
    // }
    @Operation(summary = "내 진행중인 미션 조회", description = "내가 도전 중인 미션들을 오프셋 기반 페이지네이션으로 조회한다. " +
            "JWT 도입 전 임시로 Body의 memberId를 사용한다.")
    @GetMapping("/missions/in-progress")
    public ApiResponse<OffsetPage<MemberResDTO.MissionListItem>> getMyInProgressMissions(
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        OffsetPage<MemberResDTO.MissionListItem> result = memberService.getMyInProgressMissions(authMember.getId(),
                page, size);
        return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_MISSION_LIST_OK, result);
    }
}