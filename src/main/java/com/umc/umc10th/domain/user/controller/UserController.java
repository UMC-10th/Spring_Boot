package com.umc.umc10th.domain.user.controller;

import com.umc.umc10th.domain.mission.dto.response.MissionResponseDto;
import com.umc.umc10th.domain.review.dto.response.ReviewResponseDto;
import com.umc.umc10th.domain.user.apipayload.code.UserSuccessCode;
import com.umc.umc10th.domain.user.dto.request.UserRequestDto;
import com.umc.umc10th.domain.user.dto.response.UserResponseDto;
import com.umc.umc10th.domain.user.service.UserService;
import com.umc.umc10th.global.apipayload.ApiResponse;
import com.umc.umc10th.global.apipayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/me/missions")
    public ApiResponse<MissionResponseDto.GetMissionsPaged> getMissions(
            @RequestParam Long locationId,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestBody @Valid UserRequestDto.GetMissionsRequest request) {

        return ApiResponse.onSuccess(
                UserSuccessCode.OK,
                userService.getMissions(locationId, pageSize, pageNumber, request)
        );
    }

    @GetMapping("/me/reviews")
    public ApiResponse<ReviewResponseDto.GetMyReviewsPaged> getMyReviews(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "-1") String cursor,
            @RequestParam(defaultValue = "ID") String query) {

        return ApiResponse.onSuccess(
                UserSuccessCode.OK,
                userService.getMyReviews(userId, pageSize, cursor, query)
        );
    }
//    @GetMapping("/me/missions/count")
//    public ApiResponse<MissionResponseDto.CountMissions> countMissions(@RequestParam Long locationId) {
//        BaseSuccessCode code = UserSuccessCode.OK;
//        return ApiResponse.onSuccess(code, userService.countMissions(locationId));
//    }
}
