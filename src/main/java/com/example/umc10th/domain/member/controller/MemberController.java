package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    // private final MemberService memberService;  // 6주차에 활성화하기!

    // 1. 회원가입
    @PostMapping("/auth/signup")
    public ApiResponse<MemberResDTO.SignUp> signUp(
            @RequestBody MemberReqDTO.SignUp request
    ) {
        // 6주차에서 memberService.signUp(request)로 교체
        MemberResDTO.SignUp result = MemberResDTO.SignUp.builder()
                .memberId(1L)
                .nickname(request.nickname())
                .email(request.email())
                .build();

        return ApiResponse.onSuccess(MemberSuccessCode.SIGN_UP, result);
    }

    // 2. 홈 화면 조회
    @GetMapping("/home")
    public ApiResponse<MemberResDTO.Home> getHome() {
        // 6주차에서 memberService.getHome()로 교체
        MemberResDTO.HomeMember member = MemberResDTO.HomeMember.builder()
                .memberId(1L)
                .nickname("kei")
                .point(2500)
                .build();

        MemberResDTO.MissionSummary summary = MemberResDTO.MissionSummary.builder()
                .receivedCount(7)
                .completedCount(2)
                .inProgressCount(5)
                .build();

        List<MemberResDTO.ReceivedMission> missions = List.of(
                MemberResDTO.ReceivedMission.builder()
                        .memberMissionId(101L)
                        .missionId(10L)
                        .storeId(3L)
                        .storeName("KFC")
                        .condition("10,000원 이상 주문 시")
                        .rewardPoint(500)
                        .status("IN_PROGRESS")
                        .deadline(LocalDate.of(2026, 3, 31))
                        .build()
        );

        MemberResDTO.Home result = MemberResDTO.Home.builder()
                .member(member)
                .missionSummary(summary)
                .receivedMissions(missions)
                .build();

        return ApiResponse.onSuccess(MemberSuccessCode.GET_HOME, result);
    }

    // 3. 마이페이지 조회
    @GetMapping("/members/me")
    public ApiResponse<MemberResDTO.MyPage> getMyPage() {
        // 6주차에서 memberService.getMyPage()로 교체
        MemberResDTO.MyPage result = MemberResDTO.MyPage.builder()
                .memberId(1L)
                .nickname("kei")
                .email("harim@naver.com")
                .phoneNumber("01012345678")
                .point(2500)
                .profileUrl("https://cdn.example.com/profile/default.png")
                .build();

        return ApiResponse.onSuccess(MemberSuccessCode.GET_MY_PAGE, result);
    }
}
