package com.example.week6_mission.domain.member.service;

import com.example.week6_mission.domain.member.dto.MemberReqDTO;
import com.example.week6_mission.domain.member.dto.MemberResDTO;
import com.example.week6_mission.domain.member.entity.Member;
import com.example.week6_mission.domain.member.repository.MemberRepository;
import com.example.week6_mission.domain.mission.dto.MissionResDTO;
import com.example.week6_mission.domain.mission.entity.Mission;
import com.example.week6_mission.domain.mission.exception.MissionException;
import com.example.week6_mission.domain.mission.exception.code.MissionErrorCode;
import com.example.week6_mission.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final MissionRepository missionRepository;

	@Transactional
	public MemberResDTO.SignUpRequestBody signup(MemberReqDTO.SignUpRequestBody dto) {
		Member member = Member.builder()
				.name(dto.getName())
				.gender(dto.getGender())
				.birth(dto.getBirth())
				.address(dto.getAddress())
				.detailAddress(dto.getDetailAddress())
				.socialUid(dto.getSocialUid())
				.socialType(dto.getSocialType())
				.build();

		memberRepository.save(member);

		return toSignUpResponse(dto, 0);
	}

	@Transactional(readOnly = true)
	public MissionResDTO.Mission missionSuccess(String missionId, String auth) {
		Mission mission = missionRepository.findById(Long.parseLong(missionId))
				.orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_ERROR_CODE));

		return toMissionResponse(mission.getPoint());
	}

	private MemberResDTO.SignUpRequestBody toSignUpResponse(MemberReqDTO.SignUpRequestBody dto, int point) {
		try {
			MemberResDTO.SignUpRequestBody response = new MemberResDTO.SignUpRequestBody();
			setField(response, "name", dto.getName());
			setField(response, "gender", dto.getGender());
			setField(response, "birth", dto.getBirth());
			setField(response, "address", dto.getAddress());
			setField(response, "detailAddress", dto.getDetailAddress());
			setField(response, "socialUid", dto.getSocialUid());
			setField(response, "socialType", dto.getSocialType());
			setField(response, "point", point);
			setField(response, "email", dto.getEmail());
			setField(response, "phoneNumber", dto.getPhoneNumber());
			setField(response, "profileUrl", dto.getProfileUrl());
			return response;
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException("Failed to build signup response", exception);
		}
	}

	private MissionResDTO.Mission toMissionResponse(int point) {
		try {
			MissionResDTO.Mission response = new MissionResDTO.Mission();
			setField(response, "point", point);
			return response;
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException("Failed to build mission response", exception);
		}
	}

	private void setField(Object target, String fieldName, Object value) throws ReflectiveOperationException {
		Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
	}
}
