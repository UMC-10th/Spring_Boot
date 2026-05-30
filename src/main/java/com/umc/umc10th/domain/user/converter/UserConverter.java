package com.umc.umc10th.domain.user.converter;

import com.umc.umc10th.domain.mission.dto.response.MissionResponseDto;
import com.umc.umc10th.domain.review.dto.response.ReviewResponseDto;
import com.umc.umc10th.domain.review.entity.Review;
import com.umc.umc10th.domain.user.dto.request.UserRequestDto;
import com.umc.umc10th.domain.user.entity.User;
import com.umc.umc10th.domain.user.entity.UserDoingMission;
import com.umc.umc10th.domain.user.enums.Provider;
import com.umc.umc10th.domain.user.enums.ServiceRole;
import com.umc.umc10th.domain.user.enums.SystemRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.UUID;

public class UserConverter {

    public static User toUser(UserRequestDto.CreateUser dto, String encodedPassword) {
        return User.builder()
                .loginId(dto.email())
                .password(encodedPassword)
                .uuid(UUID.randomUUID().toString())
                .provider(dto.provider() != null ? dto.provider() : Provider.LOCAL)
                .serviceRole(dto.serviceRole() != null ? dto.serviceRole() : ServiceRole.CUSTOMER)
                .systemRole(SystemRole.ROLE_USER)
                .name(dto.name())
                .sex(dto.sex())
                .birthday(dto.birthday())
                .address(dto.address())
                .phone(dto.phone())
                .build();
    }

    public static MissionResponseDto.GetMissionsPaged toGetMissionsPaged(
            Page<UserDoingMission> page) {

        List<MissionResponseDto.GetMissionsPaged.GetMission> missions =
                page.getContent().stream()
                        .map(udm -> MissionResponseDto.GetMissionsPaged.GetMission.builder()
                                .missionId(udm.getMission().getId())
                                .storeName(udm.getMission().getStore().getStoreName())
                                .title(udm.getMission().getTitle())
                                .content(udm.getMission().getContent())
                                .reward(udm.getMission().getReward())
                                .status(udm.getIsCompleted() ? "COMPLETED" : "IN_PROGRESS")
                                .build())
                        .toList();

        return MissionResponseDto.GetMissionsPaged.builder()
                .missions(missions)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }

    public static ReviewResponseDto.GetMyReviewsPaged toGetMyReviewsPaged(
            Slice<Review> slice, String nextCursor) {

        List<ReviewResponseDto.GetMyReviewsPaged.ReviewItem> reviews =
                slice.getContent().stream()
                        .map(r -> ReviewResponseDto.GetMyReviewsPaged.ReviewItem.builder()
                                .reviewId(r.getId())
                                .nickname(r.getUser().getName())
                                .stars(r.getStars().name())
                                .content(r.getContent())
                                .createdAt(r.getCreatedAt())
                                .build())
                        .toList();

        return ReviewResponseDto.GetMyReviewsPaged.builder()
                .reviews(reviews)
                .hasNext(slice.hasNext())
                .nextCursor(nextCursor)
                .pageSize(slice.getSize())
                .build();
    }
}
