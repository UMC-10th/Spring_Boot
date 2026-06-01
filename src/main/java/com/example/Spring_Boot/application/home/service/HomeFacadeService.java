package com.example.Spring_Boot.application.home.service;

import com.example.Spring_Boot.application.home.converter.HomeConverter;
import com.example.Spring_Boot.application.home.dto.HomeResDTO;
import com.example.Spring_Boot.domain.member.entity.Member;
import com.example.Spring_Boot.domain.member.exception.MemberException;
import com.example.Spring_Boot.domain.member.exception.code.MemberErrorCode;
import com.example.Spring_Boot.domain.member.repository.MemberRepository;
import com.example.Spring_Boot.domain.mission.entity.mapping.UserMission;
import com.example.Spring_Boot.domain.mission.enums.Status;
import com.example.Spring_Boot.domain.mission.exception.MissionException;
import com.example.Spring_Boot.domain.mission.exception.code.MissionErrorCode;
import com.example.Spring_Boot.domain.mission.repository.UserMissionRepository;
import com.example.Spring_Boot.domain.store.entity.Category;
import com.example.Spring_Boot.domain.store.entity.Region;
import com.example.Spring_Boot.domain.store.repository.CategoryRepository;
import com.example.Spring_Boot.domain.store.repository.RegionRepository;
import com.example.Spring_Boot.global.apiPayload.code.GeneralErrorCode;
import com.example.Spring_Boot.global.apiPayload.exception.ProjectException;
import com.example.Spring_Boot.global.security.auth.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeFacadeService {

    private static final int MAX_PAGE_SIZE = 50;

    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;
    private final UserMissionRepository userMissionRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public HomeResDTO.GetHomeResponse getHome(
            Long regionId,
            int page,
            int size,
            String authorization
    ) {
        validatePageRequest(page, size);

        Long memberId = extractMemberId(authorization);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<UserMission> userMissionPage =
                userMissionRepository.findUserMissionsByMemberIdAndRegionIdAndStatus(
                        memberId,
                        regionId,
                        Status.INCOMPLETE,
                        pageable
                );
        Long completedCount =
                userMissionRepository.countUserMissionsByMemberIdAndStatus(memberId, Status.COMPLETE);
        Map<Long, Category> categoryByStoreId = getCategoryByStoreId(userMissionPage);

        return HomeConverter.toGetHomeResponse(
                member,
                region,
                completedCount,
                userMissionPage,
                categoryByStoreId
        );
    }

    private void validatePageRequest(int page, int size) {
        if (page < 0 || size <= 0 || size > MAX_PAGE_SIZE) {
            throw new MissionException(MissionErrorCode.INVALID_PAGE_REQUEST);
        }
    }

    private Long extractMemberId(String authorization) {
        return SecurityUtil.getCurrentMemberId()
                .orElseGet(() -> extractMemberIdFromAuthorization(authorization));
    }

    private Long extractMemberIdFromAuthorization(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            throw new MemberException(MemberErrorCode.INVALID_AUTHORIZATION);
        }

        try {
            return Long.parseLong(authorization.replace("Bearer", "").trim());
        } catch (NumberFormatException e) {
            throw new MemberException(MemberErrorCode.INVALID_AUTHORIZATION);
        }
    }

    private Map<Long, Category> getCategoryByStoreId(Page<UserMission> userMissionPage) {
        List<Long> storeIds = userMissionPage.getContent().stream()
                .map(userMission -> userMission.getMission().getStore().getStoreId())
                .distinct()
                .toList();

        if (storeIds.isEmpty()) {
            return Collections.emptyMap();
        }

        return categoryRepository.findCategoriesByStoreIds(storeIds).stream()
                .collect(Collectors.toMap(
                        category -> category.getStore().getStoreId(),
                        Function.identity(),
                        (first, second) -> first
                ));
    }
}
