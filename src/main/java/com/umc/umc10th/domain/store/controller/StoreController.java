package com.umc.umc10th.domain.store.controller;

import com.umc.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.umc.umc10th.domain.review.service.ReviewService;
import com.umc.umc10th.domain.store.apipayload.code.StoreSuccessCode;
import com.umc.umc10th.domain.user.apipayload.code.UserSuccessCode;
import com.umc.umc10th.global.apipayload.ApiResponse;
import com.umc.umc10th.global.apipayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {

    private final ReviewService reviewService;

    @PostMapping("/{storeId}/review")
    public ApiResponse<Void> createReview(@PathVariable Long storeId) {
        BaseSuccessCode code = StoreSuccessCode.OK;
        reviewService.createReview(storeId);
        return ApiResponse.onSuccess(code, null);
    }
}
