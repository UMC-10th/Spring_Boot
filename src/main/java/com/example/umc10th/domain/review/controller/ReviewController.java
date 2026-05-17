package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import jakarta.validation.Valid;
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

	// 특정 가게 정보 조회
	@GetMapping("/stores/{storeId}")
	public ApiResponse<ReviewResDTO.StoreInfo> getStoreInfo(
			@PathVariable Long storeId
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				reviewService.getStoreInfo(storeId)
		);
	}

	// 특정 가게에 리뷰 작성
	@PostMapping("/stores/{storeId}/reviews")
	public ApiResponse<ReviewResDTO.CreateReview> createReview(
			@PathVariable Long storeId,
			@RequestBody @Valid ReviewReqDTO.CreateReview request
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				reviewService.createReview(storeId, request)
		);
	}

	// 내가 작성한 리뷰 목록 조회 (오프셋)
	@GetMapping("/members/me/review")
	public ApiResponse<ReviewResDTO.MyReviews> getMyReviews(
			@ModelAttribute ReviewReqDTO.MyReviewRequest request
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				reviewService.getMyReviews(request)
		);
	}

	// 내가 작성한 리뷰 목록 조회 (커서 기반)
	@PostMapping("/members/me/reviews")
	public ApiResponse<ReviewResDTO.CursorReviewList> getMyReviewsWithCursor(
			@RequestBody @Valid ReviewReqDTO.CursorReviewRequest request
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				reviewService.getMyReviewsWithCursor(request)
		);
	}
}