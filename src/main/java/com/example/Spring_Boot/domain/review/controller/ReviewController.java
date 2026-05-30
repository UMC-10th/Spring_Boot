package com.example.Spring_Boot.domain.review.controller;

import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewCursor;
import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.service.ReviewService;
import com.example.Spring_Boot.domain.user.security.AuthMember;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.CreateReviewResultDTO> createReview(
            @PathVariable Long storeId,
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody ReviewReqDTO.CreateReviewDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED,
                reviewService.createReview(authMember.getUser().getId(), storeId, request));
    }

    // query: id(ID순) 또는 rating(별점순)
    @GetMapping("/reviews/my")
    public ApiResponse<MissionResDTO.Pagination<ReviewResDTO.GetReview>> getMyReviews(
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "10") Integer pageSize,
            ReviewCursor reviewCursor
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,
                reviewService.getMyReviews(authMember.getUser().getId(), pageSize, reviewCursor));
    }
}