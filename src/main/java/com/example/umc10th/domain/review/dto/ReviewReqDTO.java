package com.example.umc10th.domain.review.dto;

public class ReviewReqDTO {

	public record CreateReview(
			Long memberMissionId,
			Double starRate,
			String content,
			java.util.List<String> photoUrls
	) {
	}

	public record MyReviewRequest(
			Integer page,
			Integer size
	) {
	}
}
