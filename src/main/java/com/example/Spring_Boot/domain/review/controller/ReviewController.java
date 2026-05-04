package com.example.Spring_Boot.domain.review.controller;

import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.exception.code.ReviewSuccessCode;
import com.example.Spring_Boot.domain.review.service.ReviewService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.BaseSuccessCode;
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
            @RequestBody ReviewReqDTO.CreateReviewRequest dto
    ) {
        BaseSuccessCode code = ReviewSuccessCode.REVIEW_CREATED;
        return ApiResponse.onSuccess(
                code,
                reviewService.createReview(storeId, authorization, dto)
        );
    }
}
