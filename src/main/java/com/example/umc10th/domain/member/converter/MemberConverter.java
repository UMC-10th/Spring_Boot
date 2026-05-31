package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.global.enums.Address;
import com.example.umc10th.global.security.dto.OAuthDTO;

import java.time.LocalDate;

public class MemberConverter {

	private MemberConverter() {
	}

	public static MemberResDTO.Login toLogin(String accessToken) {
		return new MemberResDTO.Login(accessToken);
	}

	public static Member toMember(OAuthDTO dto) {
		return Member.builder()
				.name(dto.getName())
				.nickname(dto.getName())
				.email(dto.getSocialEmail())
				.password("")
				.socialUid(dto.getSocialUid())
				.socialType(dto.getSocialType())
				.gender(Gender.NONE)
				.birth(LocalDate.of(1900, 1, 1))
				.address(Address.NONE)
				.detailAddress("")
				.point(0)
				.build();
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