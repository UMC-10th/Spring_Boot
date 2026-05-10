package com.example.umc10th.domain.review.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

	public record StoreInfo(
			Long storeId,
			String name,
			String foodType,
			String address
	) {
	}

	public record CreateReview(
			Long reviewId,
			Long storeId,
			Long memberId,
			Double starRate,
			String content,
			List<String> photoUrls,
			LocalDateTime createdAt
	) {
	}

	public record ReviewSummary(
			Long reviewId,
			Long storeId,
			String storeName,
			Double starRate,
			String content,
			LocalDateTime createdAt
	) {
	}

	public record MyReviews(
			List<ReviewSummary> reviews
	) {
	}
}
