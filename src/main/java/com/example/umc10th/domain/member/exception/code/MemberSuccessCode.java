package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    MEMBER_CREATED(
            HttpStatus.CREATED,
            "MEMBER201_1",
            "회원 가입이 성공적으로 완료되었습니다."
    ),
    MEMBER_FOUND(
            HttpStatus.OK,
            "MEMBER200_1",
            "성공적으로 회원을 조회했습니다."
    ),
    MY_PAGE_FOUND(
            HttpStatus.OK,
            "MEMBER200_2",
            "마이페이지를 성공적으로 조회했습니다."
    ),
    MEMBER_DELETED(
            HttpStatus.OK,
            "MEMBER200_3",
            "회원 탈퇴가 성공적으로 처리되었습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
