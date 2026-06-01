package com.example.mission.domain.member.exception.code;

import com.example.mission.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK, "MEMBER200_1", "성공입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
