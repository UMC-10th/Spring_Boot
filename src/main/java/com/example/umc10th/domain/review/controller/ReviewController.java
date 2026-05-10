package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {

	private final ReviewService reviewService;

	@GetMapping("/stores/{storeId}")
	public ApiResponse<ReviewResDTO.StoreInfo> getStoreInfo(
			@PathVariable Long storeId
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				reviewService.getStoreInfo(storeId)
		);
	}

	@PostMapping("/stores/{storeId}/reviews")
	public ApiResponse<ReviewResDTO.CreateReview> createReview(
			@PathVariable Long storeId,
			@RequestBody ReviewReqDTO.CreateReview request
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				reviewService.createReview(storeId, request)
		);
	}

	@GetMapping("/members/me/review")
	public ApiResponse<ReviewResDTO.MyReviews> getMyReviews(
			@ModelAttribute ReviewReqDTO.MyReviewRequest request
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				reviewService.getMyReviews(request)
		);
	}
}
