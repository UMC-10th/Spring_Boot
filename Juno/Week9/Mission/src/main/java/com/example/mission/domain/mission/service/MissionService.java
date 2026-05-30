package com.example.mission.domain.mission.service;

import com.example.mission.domain.mission.converter.MissionConverter;
import com.example.mission.domain.mission.dto.MissionReqDTO;
import com.example.mission.domain.mission.dto.MissionResDTO;
import com.example.mission.domain.mission.entity.Mission;
import com.example.mission.domain.mission.exception.MissionException;
import com.example.mission.domain.mission.exception.code.MissionErrorCode;
import com.example.mission.domain.mission.entity.mapping.MemberMission;
import com.example.mission.domain.mission.enums.MissionStatus;
import com.example.mission.domain.mission.repository.MemberMissionRepository;
import com.example.mission.domain.mission.repository.MissionRepository;
import com.example.mission.domain.store.entity.Store;
import com.example.mission.domain.store.exception.StoreException;
import com.example.mission.domain.store.exception.code.StoreErrorCode;
import com.example.mission.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    // 가게 미션 생성
    @Transactional
    public Void createMission(
            Long storeId,
            MissionReqDTO.CreateMission dto
    ){
        // 가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        // 미션 생성
        Mission mission = MissionConverter.toMission(store, dto);

        // 미션 DB 저장
        missionRepository.save(mission);
        return null;
    }

    // 가게 내 미션들 조회: 커서 기반
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
        if (!cursor.equals("-1")) {

            // 커서 분리
            String[] cursorSplit = cursor.split(":");
            switch(query.toLowerCase()) {
                case "id":

                    // 커서 타입 변환
                    Long prevCursor = Long.parseLong(cursorSplit[0]);
                    idCursor = Long.parseLong(cursorSplit[1]);

                    // 가게 내 미션들 조회 & where절에 커서값 삽입
                    missionList = missionRepository.findMissionsByStore_IdAndIdLessThanOrderByIdDesc(
                            storeId,
                            idCursor,
                            pageRequest
                    );
                    break;
                default:
                throw new MissionException(MissionErrorCode.QUERY_NOT_VALID);

            }
        } else {

            // 커서 없이 조회
            missionList = missionRepository.findMissionsByStore_IdOrderByIdDesc(storeId, pageRequest);
        }

        // 다음 커서 계산
        nextCursor = missionList.getContent().isEmpty() ? null :
                missionList.getContent().getLast().getId() + ":" + missionList.getContent().getLast().getId();


        // 응답 DTO로 포장
        return MissionConverter.toPagination(
                missionList.map(MissionConverter::toGetMission).toList(),
                missionList.hasNext(),
                nextCursor,
                missionList.getSize()
        );
    }

    // 내가 진행 중인 미션 조회
    public MissionResDTO.Pagination<MissionResDTO.GetMemberMission> getMyOngoingMissions(
            Long memberId,
            Integer pageSize,
            String cursor
    ) {
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        Slice<MemberMission> missionList;

        if (!cursor.equals("-1")) {
            Long idCursor = Long.parseLong(cursor.split(":")[0]);
            missionList = memberMissionRepository.findByMember_IdAndStatusAndIdLessThanOrderByIdDesc(
                    memberId, MissionStatus.CHALLENGING, idCursor, pageRequest
            );
        } else {
            missionList = memberMissionRepository.findByMember_IdAndStatusOrderByIdDesc(
                    memberId, MissionStatus.CHALLENGING, pageRequest
            );
        }

        String nextCursor = missionList.getContent().isEmpty() ? null :
                missionList.getContent().getLast().getId() + ":" + missionList.getContent().getLast().getId();

        return MissionConverter.toPagination(
                missionList.map(MissionConverter::toGetMemberMission).toList(),
                missionList.hasNext(),
                nextCursor,
                missionList.getSize()
        );
    }
}
