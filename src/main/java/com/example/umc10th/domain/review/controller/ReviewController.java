package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {
    private final ReviewService reviewService;

    // private final ReviewService reviewService; 6주차에 활성화

    // 6. 리뷰 작성 화면 진입용 가게 정보 조회
    @GetMapping("/stores/{storeId}")
    public ApiResponse<ReviewResDTO.StoreInfo> getStoreInfo(
            @PathVariable Long storeId
    ) {
        ReviewResDTO.StoreInfo result = reviewService.getStoreInfo(storeId);
        return ApiResponse.onSuccess(ReviewSuccessCode.GET_STORE_INFO, result);
    }

    // 7. 리뷰 작성
    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.CreateReview> createReview(
            @PathVariable Long storeId,
            @Valid @RequestBody ReviewReqDTO.CreateReview request
    ) {
        ReviewResDTO.CreateReview result = reviewService.createReview(storeId, request);
        return ApiResponse.onSuccess(ReviewSuccessCode.CREATE_REVIEW, result);
    }

    // 8. 내가 작성한 리뷰 조회 (오프셋 기반 페이지네이션)
    @GetMapping("/members/me/review")
    public ApiResponse<ReviewResDTO.MyReviewList> getMyReviews(
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        ReviewResDTO.MyReviewList result = reviewService.getMyReviews(memberId, page, size);
        return ApiResponse.onSuccess(ReviewSuccessCode.GET_MY_REVIEWS, result);
    }

    // 8.1 커서 기반 리뷰 조회 (ID / 별점 순)
    @GetMapping("/member/me/reviews/cursor")
    public ApiResponse<ReviewResDTO.MyReviewSliceList> getMyReviewsByCursor(
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "id") String sort, // "id" or "star"
            @RequestParam(required = false) String cursor, // 첫 요청은 비움
            @RequestParam(defaultValue = "10") Integer size
    ) {
        ReviewResDTO.MyReviewSliceList result = reviewService.getMyReviewsByCursor(memberId, sort, cursor, size);
        return ApiResponse.onSuccess(ReviewSuccessCode.GET_MY_REVIEWS, result);
    }
}
