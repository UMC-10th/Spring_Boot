package com.example.week6_mission.domain.store.exception.code;

import com.example.week6_mission.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {

    REVIEW_ERROR_CODE(HttpStatus.NOT_FOUND, "STORE404_1", "해당 가게를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
