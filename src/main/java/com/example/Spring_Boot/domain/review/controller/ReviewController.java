package com.example.Spring_Boot.domain.review.controller;

import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.service.ReviewService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class ReviewController {

    private final ReviewService reviewService;

    // 화면 1: 리뷰 작성
    @PostMapping("/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.CreateReviewResultDTO> createReview(
            @PathVariable Long storeId,
            @RequestParam Long userId,
            @RequestBody ReviewReqDTO.CreateReviewDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED,
                reviewService.createReview(userId, storeId, request));
    }
}