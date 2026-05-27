package com.example.mission.domain.review.controller;

import com.example.mission.domain.review.dto.ReviewResDTO;
import com.example.mission.domain.review.exception.code.ReviewSuccessCode;
import com.example.mission.domain.review.service.ReviewService;
import com.example.mission.global.apiPayload.ApiResponse;
import com.example.mission.global.apiPayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    // 내가 작성한 리뷰들 조회
    @GetMapping("/v1/members/{memberId}/reviews")
    public ApiResponse<ReviewResDTO.Pagination<ReviewResDTO.GetReview>> getMyReviews(
            @PathVariable Long memberId,
            @RequestParam Integer pageSize,
            @RequestParam String cursor
    ) {
        BaseSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.getMyReviews(memberId, pageSize, cursor));
    }
}
