package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResDTO;

public class MemberConverter {

	private MemberConverter() {
	}

	public static MemberResDTO.SignUp toSignUpResponse(
			Long memberId,
			String nickname,
			String email
	) {
		return new MemberResDTO.SignUp(memberId, nickname, email);
	}

	public static MemberResDTO.GetInfo toGetInfoResponse(
			Long memberId,
			String nickname,
			String email,
			String phoneNumber,
			Integer point,
			String profileUrl
	) {
		return new MemberResDTO.GetInfo(memberId, nickname, email, phoneNumber, point, profileUrl);
	}
}
