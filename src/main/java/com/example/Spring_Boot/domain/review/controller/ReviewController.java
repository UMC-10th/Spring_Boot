package com.example.Spring_Boot.domain.review.controller;

import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.service.ReviewService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    // 기존: 리뷰 작성 + 미션 3: @Valid 추가
    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.CreateReviewResultDTO> createReview(
            @PathVariable Long storeId,
            @RequestParam Long userId,
            @Valid @RequestBody ReviewReqDTO.CreateReviewDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED,
                reviewService.createReview(userId, storeId, request));
    }

    // 미션 2: 내가 생성한 리뷰 조회 (커서 기반)
    // GET /api/reviews/my?userId=1&pageSize=10&cursor=-1&query=id
    // query: id(ID순) 또는 rating(별점순)
    @GetMapping("/reviews/my")
    public ApiResponse<MissionResDTO.Pagination<ReviewResDTO.GetReview>> getMyReviews(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "-1") String cursor,
            @RequestParam(defaultValue = "id") String query
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,
                reviewService.getMyReviews(userId, pageSize, cursor, query));
    }
}