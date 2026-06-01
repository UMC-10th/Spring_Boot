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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/v1/stores/{storeId}/reviews")
    @Operation(summary = "리뷰 작성", description = "가게에 리뷰를 작성합니다. (사진 제외)")
    public ApiResponse<ReviewResDTO.WriteReviewRes> writeReview(
            @PathVariable Long storeId,
            @RequestParam Long memberId,
            @RequestBody @Valid ReviewReqDTO.WriteReviewReq request
    ) {
        Review review = reviewService.writeReview(storeId, memberId, request);
        return ApiResponse.onSuccess(ReviewSuccessCode.WRITE_REVIEW, ReviewConverter.toWriteReviewRes(review));
    }

    @GetMapping("/api/v1/members/{memberId}/reviews")
    @Operation(summary = "내가 작성한 리뷰 목록 조회",
               description = "커서 기반 페이지네이션으로 내가 작성한 리뷰를 조회합니다.\n\n" +
                             "- sortType: ID (기본값) 또는 SCORE\n" +
                             "- cursor: ID 정렬 시 'ID:reviewId', SCORE 정렬 시 'SCORE:score:reviewId'. 최초 조회 시 생략 또는 -1")
    public ApiResponse<ReviewResDTO.MyReviewListRes> getMyReviews(
            @PathVariable Long memberId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "ID") String sortType,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        ReviewResDTO.MyReviewListRes result = reviewService.getMyReviews(memberId, cursor, sortType, pageSize);
        return ApiResponse.onSuccess(ReviewSuccessCode.GET_MY_REVIEWS, result);
    }
}
