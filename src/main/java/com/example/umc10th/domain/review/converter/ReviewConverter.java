package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.mission.entity.Store;
import org.springframework.data.domain.Page;

import java.util.List;

public class ReviewConverter {

	private ReviewConverter() {
	}

	public static ReviewResDTO.StoreInfo toStoreInfoResponse(Store store) {
		return new ReviewResDTO.StoreInfo(
				store.getId(),
				store.getName(),
				store.getFoodType().name(),
				store.getLocation().getName().getDisplayName() + " " + store.getDetailAddress()
		);
	}

	public static ReviewResDTO.CreateReview toCreateReviewResponse(Review review, List<String> photoUrls) {
		return new ReviewResDTO.CreateReview(
				review.getId(),
				review.getStore().getId(),
				review.getMember().getId(),
				review.getStar().doubleValue(),
				review.getContent(),
				photoUrls,
				review.getCreatedAt()
		);
	}

	public static ReviewResDTO.ReviewSummary toReviewSummary(Review review) {
		return new ReviewResDTO.ReviewSummary(
				review.getId(),
				review.getStore().getId(),
				review.getStore().getName(),
				review.getStar().doubleValue(),
				review.getContent(),
				review.getCreatedAt()
		);
	}

	public static ReviewResDTO.MyReviews toMyReviewsResponse(Page<Review> page) {
		List<ReviewResDTO.ReviewSummary> reviews = page.getContent().stream()
				.map(ReviewConverter::toReviewSummary)
				.toList();

		return new ReviewResDTO.MyReviews(reviews);
	}
}