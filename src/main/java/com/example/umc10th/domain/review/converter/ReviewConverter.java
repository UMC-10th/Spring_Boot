package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewConverter {

	private ReviewConverter() {
	}

	public static ReviewResDTO.StoreInfo toStoreInfoResponse(Long storeId) {
		return new ReviewResDTO.StoreInfo(storeId, "신승호라멘", "KOREAN", "서울 중구 ...");
	}

	public static ReviewResDTO.CreateReview toCreateReviewResponse(
			Long storeId,
			ReviewReqDTO.CreateReview request
	) {
		return new ReviewResDTO.CreateReview(
				55L,
				storeId,
				1L,
				request.starRate(),
				request.content(),
				request.photoUrls() == null ? List.of() : request.photoUrls(),
				LocalDateTime.of(2026, 3, 25, 15, 20, 0)
		);
	}

	public static ReviewResDTO.MyReviews toMyReviewsResponse(ReviewReqDTO.MyReviewRequest request) {
		ReviewResDTO.ReviewSummary reviewSummary = new ReviewResDTO.ReviewSummary(
				55L,
				3L,
				"현안국밥",
				4.5,
				"음식이 맛있고 직원분이 친절했어요.",
				LocalDateTime.of(2026, 3, 25, 15, 20, 0)
		);
		return new ReviewResDTO.MyReviews(List.of(reviewSummary));
	}
}
