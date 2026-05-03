package com.example.Spring_Boot.domain.user.controller;

import com.example.Spring_Boot.domain.user.dto.UserReqDTO;
import com.example.Spring_Boot.domain.user.dto.UserResDTO;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    @PostMapping("/users")
    public ApiResponse<UserResDTO.JoinResultDTO> join(
            @RequestBody UserReqDTO.JoinDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, null);
    }
}