package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    MEMBER_JOIN_OK(HttpStatus.CREATED, "MEMBER201_1", "성공적으로 회원가입했습니다."),
    MEMBER_LOGIN_OK(HttpStatus.OK, "MEMBER200_1", "성공적으로 로그인했습니다."),
    MEMBER_FETCH_OK(HttpStatus.OK, "MEMBER200_2", "성공적으로 유저를 조회했습니다."),
    MEMBER_MISSION_LIST_OK(HttpStatus.OK, "MEMBER200_3", "성공적으로 미션 목록을 조회했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}