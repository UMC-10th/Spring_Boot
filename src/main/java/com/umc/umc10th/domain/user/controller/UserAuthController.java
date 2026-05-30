package com.umc.umc10th.domain.user.controller;

import com.umc.umc10th.domain.user.apipayload.code.UserSuccessCode;
import com.umc.umc10th.domain.user.dto.request.UserRequestDto;
import com.umc.umc10th.domain.user.service.UserService;
import com.umc.umc10th.global.apipayload.ApiResponse;
import com.umc.umc10th.global.apipayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<Void> signup(@Valid @RequestBody UserRequestDto.CreateUser dto) {
        BaseSuccessCode code = UserSuccessCode.OK;
        userService.createUser(dto);
        return ApiResponse.onSuccess(code);
    }
}
