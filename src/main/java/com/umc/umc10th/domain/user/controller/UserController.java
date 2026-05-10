package com.umc.umc10th.domain.user.controller;

import com.umc.umc10th.domain.mission.dto.response.MissionResponseDto;
import com.umc.umc10th.domain.user.apipayload.code.UserSuccessCode;
import com.umc.umc10th.domain.user.dto.response.UserResponseDto;
import com.umc.umc10th.domain.user.service.UserService;
import com.umc.umc10th.global.apipayload.ApiResponse;
import com.umc.umc10th.global.apipayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserResponseDto.GetInfo> getInfo() {
        BaseSuccessCode code = UserSuccessCode.OK;
        return ApiResponse.onSuccess(code, userService.getInfo());
    }

    @GetMapping("/me/missions")
    public ApiResponse<MissionResponseDto.GetMissions> getMissions(@RequestParam Long locationId, @RequestParam String status) {
        BaseSuccessCode code = UserSuccessCode.OK;
        return ApiResponse.onSuccess(code, userService.getMissions(locationId, status));
    }

//    @GetMapping("/me/missions/count")
//    public ApiResponse<MissionResponseDto.CountMissions> countMissions(@RequestParam Long locationId) {
//        BaseSuccessCode code = UserSuccessCode.OK;
//        return ApiResponse.onSuccess(code, userService.countMissions(locationId));
//    }
}
