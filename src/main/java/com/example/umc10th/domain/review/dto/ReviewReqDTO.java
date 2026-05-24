package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class ReviewReqDTO {

	public record CreateReview(
			Long memberMissionId,
			@NotNull(message = "별점은 필수입니다.")
			@DecimalMin(value = "0.0", message = "별점은 0.0 이상이어야 합니다.")
			@DecimalMax(value = "5.0", message = "별점은 5.0 이하여야 합니다.")
			Double starRate,
			@NotBlank(message = "리뷰 내용은 필수입니다.") String content,
			List<String> photoUrls
	) {
	}

	public record MyReviewRequest(
			Integer page,
			Integer size
	) {
	}

	public record CursorReviewRequest(
			@NotNull(message = "회원 ID는 필수입니다.")
			Long memberId,
			@Positive(message = "size는 1 이상이어야 합니다.")
			Integer size,
			String sortType,
			Long cursorId,
			Double cursorStar
	) {
	}
}
