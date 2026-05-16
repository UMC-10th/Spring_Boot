package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/mine")
    public ApiResponse<ReviewResDTO.CursorPagination<ReviewResDTO.MyReviewDTO>> getMyReviews(
            @RequestParam @NotNull(message = "회원 ID는 필수입니다.") Long memberId,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer pageSize,
            @RequestParam(defaultValue = "-1") String cursor,
            @RequestParam @NotNull(message = "정렬(sort)은 필수입니다. id 또는 rating") String sort
    ) {
        return ApiResponse.onSuccess(
                ReviewSuccessCode.MY_REVIEWS_FOUND,
                reviewService.getMyReviews(memberId, pageSize, cursor, sort)
        );
    }

    @PostMapping
    public ApiResponse<ReviewResDTO.CreateReviewResultDTO> createReview(
            @RequestBody @Valid ReviewReqDTO.CreateReviewDTO request
    ) {

        return ApiResponse.onSuccess(
                ReviewSuccessCode.REVIEW_CREATED,
                reviewService.createReview(request)
        );
    }
}
