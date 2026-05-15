package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.enums.ReviewSortType;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.dto.CursorPage;
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

    @Operation(
            summary = "내가 작성한 리뷰 조회",
            description = "커서 기반 페이지네이션. sortBy=ID 또는 RATING으로 정렬 변경. " +
                    "첫 요청은 커서를 비우고, 응답의 nextCursor를 그대로 다음 요청에 넣으면 된다."
    )
    @GetMapping("/my")
    public ApiResponse<CursorPage<ReviewResDTO.MyReviewItem>> getMyReviews(
            @Valid @RequestBody ReviewReqDTO.MyReviews request,
            @RequestParam(defaultValue = "ID") ReviewSortType sortBy,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) Integer cursorRating,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        CursorPage<ReviewResDTO.MyReviewItem> result = reviewService.getMyReviews(
                request.memberId(), sortBy, cursorId, cursorRating, size
        );
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_LIST_OK, result);
    }
}