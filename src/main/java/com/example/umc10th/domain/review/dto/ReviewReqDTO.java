package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ReviewReqDTO {

	public record CreateReview(
			Long memberMissionId,
			@NotNull @DecimalMin("0.0") @DecimalMax("5.0") Double starRate,
			@NotBlank String content,
			List<String> photoUrls
	) {
	}

	public record MyReviewRequest(
			Integer page,
			Integer size
	) {
	}
}
