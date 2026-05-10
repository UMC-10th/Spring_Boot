package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

	REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404_1", "존재하지 않는 리뷰입니다."),
	REVIEW_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "REVIEW400_1", "잘못된 리뷰 요청입니다."),
	REVIEW_ALREADY_EXISTS(HttpStatus.CONFLICT, "REVIEW409_1", "이미 작성된 리뷰입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
