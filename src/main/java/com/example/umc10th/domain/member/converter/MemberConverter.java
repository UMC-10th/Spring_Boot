package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;

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

	public static MemberResDTO.MyPage toMyPageResponse(Member member) {
		return new MemberResDTO.MyPage(
				member.getId(),
				member.getNickname(),
				member.getEmail(),
				member.getPhoneNumber(),
				member.getPoint(),
				member.getProfileUrl()
		);
	}
}