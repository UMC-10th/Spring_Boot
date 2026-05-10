package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    public MemberResDTO.GetInfo getInfo() {
        return MemberConverter.toGetInfoResponse(
                1L,
                "gimeung",
                "test@gmail.com",
                "01012345678",
                2500,
                "https://cdn.example.com/profile/default.png"
        );
    }

    public MemberResDTO.SignUp signUp(MemberReqDTO.SignUp dto) {
        return MemberConverter.toSignUpResponse(
                1L,
                dto.nickname(),
                dto.email()
        );
    }
}
