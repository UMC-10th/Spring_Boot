package com.example.mission.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.mission.domain.store.dto.StoreReqDTO;
import com.example.mission.domain.store.dto.StoreResDTO;
import com.example.mission.domain.store.service.StoreService;
import com.example.mission.global.apiPayload.ApiResponse;
import com.example.mission.global.apiPayload.code.BaseSuccessCode;
import com.example.mission.global.apiPayload.code.GeneralSuccessCode;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    // 리뷰 작성
    // Path Variable
    @PostMapping("/{storeId}/reviews")
    public ApiResponse<StoreResDTO.Review> postReview(
            @PathVariable String storeId,
            @RequestHeader("Authorization") String auth,
            @RequestBody StoreReqDTO.PostReview dto
    ){
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, storeService.postReview(storeId, auth, dto));
    }
}
