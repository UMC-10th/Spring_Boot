package com.example.Spring_Boot.application.home.controller;

import com.example.Spring_Boot.application.home.dto.HomeResDTO;
import com.example.Spring_Boot.application.home.service.HomeFacadeService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
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

    private final HomeFacadeService homeFacadeService;

    @GetMapping
    public ApiResponse<HomeResDTO.GetHomeResponse> getHome(
            @RequestParam Long regionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("Authorization") String authorization
    ) {
        HomeResDTO.GetHomeResponse response = homeFacadeService.getHome(regionId, page, size, authorization);

        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                response
        );
    }
}
