package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReviewController {

    // private final ReviewService reviewService; 6주차에 활성화

    // 6. 리뷰 작성 화면 진입용 가게 정보 조회
    @GetMapping("/stores/{storeId}")
    public ApiResponse<ReviewResDTO.StoreInfo> getStoreInfo(
            @PathVariable Long storeId
    ) {
        // TODO: 6주차에서 reviewService.getStoreInfo(storeId)로 교체
        ReviewResDTO.StoreInfo result = ReviewResDTO.StoreInfo.builder()
                .storeId(storeId)
                .name("신승호라멘")
                .foodType("KOREAN")
                .address("서울 중구 ...")
                .build();

        return ApiResponse.onSuccess(ReviewSuccessCode.GET_STORE_INFO, result);
    }

    // 7. 리뷰 작성
    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.CreateReview> createReview(
            @PathVariable Long storeId,
            @RequestBody ReviewReqDTO.CreateReview request
    ) {
        // 6주차에서 reviewService.createReview(storeId, request)로 교체
        ReviewResDTO.CreateReview result = ReviewResDTO.CreateReview.builder()
                .reviewId(55L)
                .storeId(storeId)
                .memberId(1L)
                .starRate(request.starRate())
                .content(request.content())
                .photoUrls(request.photoUrls())
                .createdAt(LocalDateTime.now())
                .build();

        return ApiResponse.onSuccess(ReviewSuccessCode.CREATE_REVIEW, result);
    }

    // 8. 내가 작성한 리뷰 조회
    @GetMapping("/members/me/review")
    public ApiResponse<ReviewResDTO.MyReviewList> getMyReviews(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        // 6주차에서 reviewService.getMyReviews(page, size)로 교체
        List<ReviewResDTO.MyReviewInfo> reviews = List.of(
                ReviewResDTO.MyReviewInfo.builder()
                        .reviewId(55L)
                        .storeId(3L)
                        .storeName("현안국밥")
                        .starRate(new BigDecimal("4.5"))
                        .content("음식이 맛있고 직원분이 친절했어요.")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        ReviewResDTO.MyReviewList result = ReviewResDTO.MyReviewList.builder()
                .reviews(reviews)
                .build();

        return ApiResponse.onSuccess(ReviewSuccessCode.GET_MY_REVIEWS, result);
    }
}
