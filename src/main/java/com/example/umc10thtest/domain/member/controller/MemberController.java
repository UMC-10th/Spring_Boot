package com.example.umc10thtest.domain.member.controller;

import com.example.umc10thtest.domain.member.converter.MemberConverter;
import com.example.umc10thtest.domain.member.dto.MemberReqDTO;
import com.example.umc10thtest.domain.member.dto.MemberResDTO;
import com.example.umc10thtest.domain.member.entity.Member;
import com.example.umc10thtest.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10thtest.domain.member.service.MemberService;
import com.example.umc10thtest.domain.mission.converter.MissionConverter;
import com.example.umc10thtest.domain.mission.entity.mapping.MemberMission;
import com.example.umc10thtest.domain.mission.enums.MissionStatus;
import com.example.umc10thtest.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/me")
    @Operation(summary = "유저 조회 (mock)", description = "ID로 유저 정보를 조회합니다. (5주차 mock)")
    public ApiResponse<MemberResDTO.GetMemberRes> getMember(
            @RequestBody MemberReqDTO.GetMemberReq request
    ) {
        MemberResDTO.GetMemberRes result = MemberResDTO.GetMemberRes.builder()
                .name("nickname012")
                .profileUrl("https://example.com/profile.jpg")
                .email("user@example.com")
                .phoneNumber(null)
                .point(2500)
                .build();
        return ApiResponse.onSuccess(MemberSuccessCode.GET_MEMBER, result);
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "마이 페이지 조회", description = "회원의 마이 페이지 정보를 조회합니다.")
    public ApiResponse<MemberResDTO.MyPageRes> getMyPage(@PathVariable Long memberId) {
        Member member = memberService.getMember(memberId);
        return ApiResponse.onSuccess(MemberSuccessCode.GET_MY_PAGE, MemberConverter.toMyPageRes(member));
    }

    @GetMapping("/{memberId}/missions")
    @Operation(summary = "내 미션 목록 조회", description = "진행중 또는 완료한 미션 목록을 페이징으로 조회합니다.")
    public ApiResponse<MemberResDTO.MissionPreviewListRes> getMyMissions(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "CHALLENGING") MissionStatus status,
            @RequestParam(defaultValue = "0") Integer page
    ) {
        Page<MemberMission> missionPage = memberService.getMyMissions(memberId, status, page);
        return ApiResponse.onSuccess(MemberSuccessCode.GET_MY_MISSIONS, MissionConverter.toMyMissionPreviewListRes(missionPage));
    }
}
