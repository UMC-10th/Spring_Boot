package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "리뷰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성", description = "가게에 대한 리뷰를 작성한다.")
    @PostMapping
    public ApiResponse<ReviewResDTO.CreateResult> create(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody ReviewReqDTO.Create request
    ) {
        Long userId = 1L;
        ReviewResDTO.CreateResult result = reviewService.create(userId, request);
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_CREATED, result);
    }
}