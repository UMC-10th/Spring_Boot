package com.example.umc10thtest.domain.member.exception.code;

import com.example.umc10thtest.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {
    GET_MEMBER(HttpStatus.OK, "MEMBER200_1", "성공적으로 유저를 조회했습니다."),
    GET_MY_PAGE(HttpStatus.OK, "MEMBER200_2", "마이 페이지 조회 성공"),
    GET_MY_MISSIONS(HttpStatus.OK, "MEMBER200_3", "내 미션 목록 조회 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
