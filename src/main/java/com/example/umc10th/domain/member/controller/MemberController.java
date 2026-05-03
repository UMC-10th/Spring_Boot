package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/users")
    public ApiResponse<String> createUser() {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                memberService.createUser()
        );
    }

    @GetMapping("/test")
    public ApiResponse<String> test() {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                "test"
        );
    }

    @PostMapping("/query-parameter")
    public ApiResponse<String> queryParameter(
            @RequestParam String queryParameter
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                memberService.singleParameter(queryParameter)
        );
    }

    @PostMapping("/request-body")
    public ApiResponse<MemberResDTO.RequestBody> requestBody(
            @RequestBody MemberReqDTO.RequestBody dto
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                memberService.requestBody(dto)
        );
    }

    @PostMapping("/{pathVariable}")
    public ApiResponse<String> pathVariable(
            @PathVariable String pathVariable
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                memberService.singleParameter(pathVariable)
        );
    }

    @PostMapping("/header")
    public ApiResponse<String> header(
            @RequestHeader("test") String test
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                memberService.singleParameter(test)
        );
    }
}