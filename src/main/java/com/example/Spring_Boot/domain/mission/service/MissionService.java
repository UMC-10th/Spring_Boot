package com.example.Spring_Boot.domain.mission.service;

import com.example.Spring_Boot.domain.mission.converter.MissionConverter;
import com.example.Spring_Boot.domain.mission.dto.MissionReqDTO;
import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.mission.entity.mapping.UserMission;
import com.example.Spring_Boot.domain.mission.enums.Status;
import com.example.Spring_Boot.domain.mission.exception.MissionException;
import com.example.Spring_Boot.domain.mission.exception.code.MissionErrorCode;
import com.example.Spring_Boot.domain.mission.repository.UserMissionRepository;
import com.example.Spring_Boot.domain.store.entity.Category;
import com.example.Spring_Boot.domain.store.repository.CategoryRepository;
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
public class MissionService {

    private static final int MAX_PAGE_SIZE = 50;

    private final UserMissionRepository userMissionRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public MissionResDTO.MissionListResponse getInProgressMissions(
            MissionReqDTO.InProgressMissionRequest request,
            int page,
            int size
    ) {
        validatePageRequest(page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<UserMission> userMissionPage =
                userMissionRepository.findUserMissionsByMemberIdAndStatus(
                        request.userId(),
                        Status.INCOMPLETE,
                        pageable
                );
        Map<Long, Category> categoryByStoreId = getCategoryByStoreId(userMissionPage);

        return MissionConverter.toMissionListResponse(userMissionPage, categoryByStoreId);
    }

    @Transactional(readOnly = true)
    public MissionResDTO.MissionListResponse getUserMissions(
            Status status,
            int page,
            int size,
            String authorization
    ) {
        validatePageRequest(page, size);

        Long memberId = extractMemberId(authorization);
        Pageable pageable = PageRequest.of(page, size);
        Page<UserMission> userMissionPage =
                userMissionRepository.findUserMissionsByMemberIdAndStatus(memberId, status, pageable);
        Map<Long, Category> categoryByStoreId = getCategoryByStoreId(userMissionPage);

        return MissionConverter.toMissionListResponse(userMissionPage, categoryByStoreId);
    }

    public MissionResDTO.MissionSuccessResponse completeUserMission(Long userMissionId, String authorization, MissionReqDTO.MissionSuccessRequest request) {
        return null;
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
            throw new MissionException(MissionErrorCode.INVALID_AUTHORIZATION);
        }

        try {
            return Long.parseLong(authorization.replace("Bearer", "").trim());
        } catch (NumberFormatException e) {
            throw new MissionException(MissionErrorCode.INVALID_AUTHORIZATION);
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
