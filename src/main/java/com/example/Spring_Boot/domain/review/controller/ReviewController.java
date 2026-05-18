package com.example.Spring_Boot.domain.review.controller;

import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.enums.ReviewSortType;
import com.example.Spring_Boot.domain.review.exception.code.ReviewSuccessCode;
import com.example.Spring_Boot.domain.review.service.ReviewService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.CreateReviewResponse> createReview(
            @PathVariable("storeId") Integer storeId,
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody ReviewReqDTO.CreateReviewRequest dto
    ) {
        BaseSuccessCode code = ReviewSuccessCode.REVIEW_CREATED;
        return ApiResponse.onSuccess(
                code,
                reviewService.createReview(storeId, authorization, dto)
        );
    }

    // 내가 작성한 리뷰 목록 조회
    @PostMapping("/reviews/me")
    public ApiResponse<ReviewResDTO.MyReviewListResponse> getMyReviews(
            @Valid @RequestBody ReviewReqDTO.MyReviewRequest request,
            @RequestParam(defaultValue = "ID") ReviewSortType sort,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(
                ReviewSuccessCode.MY_REVIEW_LIST_OK,
                reviewService.getMyReviews(request, sort, cursor, size)
        );
    }
}
