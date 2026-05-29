package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.StoreException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.exception.code.StoreErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRespository;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th.global.cursor.Cursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRespository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    // 홈 화면
    public MissionResDTO.GetHome getHome(MissionReqDTO.GetHome dto, Pageable pageable) {

        Member member = memberRepository.findById(Math.toIntExact(dto.memberId()))
                .orElseThrow(() -> new RuntimeException(GeneralErrorCode.NOT_FOUND.getMessage()));

        Page<Mission> missionPage = missionRepository.findByStore_Location_Id(
                Math.toIntExact(dto.locationId()), pageable
        );

        return MissionConverter.toGetHome(member, missionPage);
    }

    // 내 미션 목록
    public MissionResDTO.GetMyMissions getMyMissions(MissionReqDTO.GetMyMissions dto, Pageable pageable) {

        Page<MemberMission> memberMissionPage;

        if (dto.isComplete() == null) {
            memberMissionPage = memberMissionRepository.findByMember_Id(dto.memberId(), pageable);
        } else {
            memberMissionPage = memberMissionRepository.findByMember_IdAndIsComplete(
                    dto.memberId(), dto.isComplete(), pageable
            );
        }

        return MissionConverter.toGetMyMissions(memberMissionPage);
    }

    @Transactional
    public MissionResDTO.CompleteMission completeMission(Long missionId) {
        // TODO: 구현 예정
        return null;
    }

    // 가게 미션 생성
    @Transactional
    public Void createMission(
            Long storeId,
            MissionReqDTO.CreateMission dto
    ){
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        Mission mission = MissionConverter.toMission(store, dto);

        missionRepository.save(mission);
        return null;
    }

    // 가게 내 미션들 조회
    public MissionResDTO.Pagination<MissionResDTO.GetMission> getMissions(
            Long storeId,
            Cursor cursor
    ) {

        PageRequest pageRequest = cursor.toPageRequest();

        Slice<Mission> missionList;

        if (!cursor.firstPage()) {
            switch (cursor.queryLowerCase()) {
                case "id":
                    missionList = missionRepository.findMissionByStore_IdAndIdLessThanOrderByIdDesc(
                            storeId,
                            cursor.idCursor(),
                            pageRequest
                    );
                    break;
                default:
                    throw new MissionException(MissionErrorCode.QUERY_NOT_VALID);
            }
        } else {
            missionList = missionRepository.findMissionByStore_IdOrderByIdDesc(storeId, pageRequest);
        }

        List<Mission> content = missionList.getContent();
        String nextCursor = "";

        if (!content.isEmpty()) {
            Mission lastMission = content.getLast();
            nextCursor = lastMission.getId() + ":" + lastMission.getId();
        }

        return MissionConverter.toPagination(
                missionList.map(MissionConverter::toGetMission).toList(),
                missionList.hasNext(),
                nextCursor,
                cursor.pageSize()
        );
    }

}
