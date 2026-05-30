package com.example.mission.domain.mission.service;

import com.example.mission.domain.mission.converter.MissionConverter;
import com.example.mission.domain.mission.dto.MissionReqDTO;
import com.example.mission.domain.mission.dto.MissionResDTO;
import com.example.mission.domain.mission.entity.Mission;
import com.example.mission.domain.mission.exception.MissionException;
import com.example.mission.domain.mission.exception.code.MissionErrorCode;
import com.example.mission.domain.mission.repository.MissionRepository;
import com.example.mission.domain.store.entity.Store;
import com.example.mission.domain.store.exception.StoreException;
import com.example.mission.domain.store.exception.code.StoreErrorCode;
import com.example.mission.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

	private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

	public MissionResDTO.MissionList missions(String auth, String storeId) {
		Long id = Long.parseLong(storeId);
		List<Mission> missions = missionRepository.findAllByStore_Id(id);

		MissionResDTO.Mission[] missionResponses = missions.stream()
				.map(mission -> toMissionResponse(mission.getPoint()))
				.toArray(MissionResDTO.Mission[]::new);

		return toMissionListResponse(missionResponses);
	}

	private MissionResDTO.Mission toMissionResponse(int point) {
		try {
			MissionResDTO.Mission response = new MissionResDTO.Mission();
			Field pointField = MissionResDTO.Mission.class.getDeclaredField("point");
			pointField.setAccessible(true);
			pointField.setInt(response, point);
			return response;
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException("Failed to build mission response", exception);
		}
	}

	private MissionResDTO.MissionList toMissionListResponse(MissionResDTO.Mission[] missionResponses) {
		try {
			MissionResDTO.MissionList response = new MissionResDTO.MissionList();
			Field missionListField = MissionResDTO.MissionList.class.getDeclaredField("missionList");
			missionListField.setAccessible(true);
			missionListField.set(response, missionResponses);
			return response;
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException("Failed to build mission list response", exception);
		}
	}

    // 가게 미션 생성
    @Transactional
    public Void createMission(
            Long storeId,
            MissionReqDTO.CreateMission dto
    ) {
        // 가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        // 미션 생성
        Mission mission = MissionConverter.toMission(store, dto);

        // 미션 DB 저장
        missionRepository.save(mission);
        return null;
    }

    public MissionResDTO.Pagination<MissionResDTO.GetMission> getMissions(
            Long storeId,
            Integer pageSize,
            String cursor,
            String query
    ){

        // 페이지 정보들을 PageRequest로 만들기
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        long idCursor;
        Slice<Mission> missionList;
        String nextCursor;

        // 커서가 있는 경우
        if(!cursor.equals("-1")) {

            // 커서 분리
            String[] cursorSplit = cursor.split(":");
            switch (query.toLowerCase()) {
                case "id":

                    // 커서 타입 변환
                    Long prevCursor = Long.parseLong(cursorSplit[0]);
                    idCursor = Long.parseLong(cursorSplit[1]);

                    // 가게 내 미션들 조회 & where절에 커서값 기입
                    missionList = missionRepository.findMissionsByStore_IdAndIdLessThanOrderByIdDesc(
                            storeId,
                            idCursor,
                            pageRequest
                    );
                    break;
                default:
                    throw new MissionException(MissionErrorCode.QUERY_NOT_VALID);
            }
        }

        // 다음 커서 계산
        nextCursor = missionList.getContent().getLast().getId() + ":" + missionList.getContent().getLast().getId();

        // 미션들 응답 DTO로 포장하기
        return MissionConverter.toPagination(
                missionList.map(MissionConverter::toGetMission).toList(),
                missionList.hasNext(),
                nextCursor,
                missionList.getSize()
        );
    }
}
