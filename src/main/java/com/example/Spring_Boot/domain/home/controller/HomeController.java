package com.example.Spring_Boot.domain.home.controller;

import com.example.Spring_Boot.domain.home.dto.HomeResDTO;
import com.example.Spring_Boot.domain.home.exception.code.HomeSuccessCode;
import com.example.Spring_Boot.domain.home.service.HomeService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public ApiResponse<HomeResDTO.GetHomeResponse> getHome(
            @RequestParam Long regionId,
            @RequestHeader("Authorization") String authorization
    ) {
        HomeResDTO.GetHomeResponse response = homeService.getHome(regionId, authorization);

        return ApiResponse.onSuccess(
                HomeSuccessCode.HOME_GET_OK,
                response
        );
    }
}
