package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

	public ReviewResDTO.StoreInfo getStoreInfo(Long storeId) {
		return ReviewConverter.toStoreInfoResponse(storeId);
	}

	public ReviewResDTO.CreateReview createReview(Long storeId, ReviewReqDTO.CreateReview request) {
		return ReviewConverter.toCreateReviewResponse(storeId, request);
	}

	public ReviewResDTO.MyReviews getMyReviews(ReviewReqDTO.MyReviewRequest request) {
		return ReviewConverter.toMyReviewsResponse(request);
	}
}
