package com.example.umc10thtest.domain.review.controller;

import com.example.umc10thtest.domain.review.converter.ReviewConverter;
import com.example.umc10thtest.domain.review.dto.ReviewReqDTO;
import com.example.umc10thtest.domain.review.dto.ReviewResDTO;
import com.example.umc10thtest.domain.review.entity.Review;
import com.example.umc10thtest.domain.review.service.ReviewService;
import com.example.umc10thtest.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10thtest.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{storeId}/reviews")
    @Operation(summary = "리뷰 작성", description = "가게에 리뷰를 작성합니다. (사진 제외)")
    public ApiResponse<ReviewResDTO.WriteReviewRes> writeReview(
            @PathVariable Long storeId,
            @RequestParam Long memberId,
            @RequestBody ReviewReqDTO.WriteReviewReq request
    ) {
        Review review = reviewService.writeReview(storeId, memberId, request);
        return ApiResponse.onSuccess(ReviewSuccessCode.WRITE_REVIEW, ReviewConverter.toWriteReviewRes(review));
    }
}
