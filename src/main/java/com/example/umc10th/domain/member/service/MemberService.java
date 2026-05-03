package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberService {

    public MemberResDTO.JoinResult join(MemberReqDTO.Join request) {
        // TODO: 다음 주차에서 Repository 연결
        return MemberResDTO.JoinResult.builder()
                .userId(1L)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public MemberResDTO.LoginResult login(MemberReqDTO.Login request) {
        // TODO: 다음 주차에서 Repository 연결
        return MemberResDTO.LoginResult.builder()
                .accessToken("dummy-token")
                .tokenType("Bearer")
                .build();
    }

    public MemberResDTO.MyPage getMyPage(Long userId) {
        // TODO: 다음 주차에서 Repository 연결
        return MemberResDTO.MyPage.builder()
                .userId(userId)
                .name("테스트")
                .email("test@example.com")
                .point(0)
                .reviewCount(0)
                .build();
    }

    public MemberResDTO.MissionList getMyMissions(Long userId, String status, Integer page, Integer size) {
        // TODO: 다음 주차에서 Repository 연결
        return MemberResDTO.MissionList.builder()
                .missions(Collections.emptyList())
                .totalCount(0L)
                .currentPage(page)
                .build();
    }
}