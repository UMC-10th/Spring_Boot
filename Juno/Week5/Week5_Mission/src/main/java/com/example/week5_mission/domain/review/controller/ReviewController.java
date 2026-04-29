package com.example.week5_mission.domain.review.controller;

import com.example.week5_mission.domain.review.dto.ReviewReqDTO;
import com.example.week5_mission.domain.review.dto.ReviewResDTO;
import com.example.week5_mission.domain.review.service.ReviewService;
import com.example.week5_mission.global.apiPayload.ApiResponse;
import com.example.week5_mission.global.apiPayload.code.BaseSuccessCode;
import com.example.week5_mission.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    // Path Variable
    @PostMapping("/{storeId}")
    public ApiResponse<ReviewResDTO.Review> postReview(
            @PathVariable String storeId,
            @RequestHeader("Authorization") String auth,
            @RequestBody ReviewReqDTO.PostReview dto
    ){
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.postReview(storeId, auth, dto));
    }
}
