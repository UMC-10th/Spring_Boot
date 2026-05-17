package com.umc.umc10th.domain.user.service;

import com.umc.umc10th.domain.mission.dto.response.MissionResponseDto;
import com.umc.umc10th.domain.review.dto.response.ReviewResponseDto;
import com.umc.umc10th.domain.review.entity.Review;
import com.umc.umc10th.domain.review.enums.Stars;
import com.umc.umc10th.domain.review.repository.ReviewRepository;
import com.umc.umc10th.domain.user.converter.UserConverter;
import com.umc.umc10th.domain.user.dto.request.UserRequestDto;
import com.umc.umc10th.domain.user.dto.response.UserResponseDto;
import com.umc.umc10th.domain.user.entity.User;
import com.umc.umc10th.domain.user.entity.UserDoingMission;
import com.umc.umc10th.domain.user.repository.UserDoingMissionRepository;
import com.umc.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserDoingMissionRepository userDoingMissionRepository;
    private final ReviewRepository reviewRepository;

    // 임시 인증 유저 ID - 실제로는 SecurityContextHolder 등에서 추출
    private static final Long TEMP_USER_ID = 1L;
    private static final int PAGE_SIZE = 10;

    public UserResponseDto.GetInfo getInfo() {
        User user = userRepository.findUserInfo(TEMP_USER_ID)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return UserResponseDto.GetInfo.builder()
                .nickname(user.getName())
                .email(user.getLoginId())
                .phone(user.getPhone() != null ? user.getPhone() : "미이용")
                .points(user.getPoints())
                .build();
    }

    public MissionResponseDto.GetMissions getMissions(Long locationId, String status) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<UserDoingMission> page = userDoingMissionRepository.findMyMissions(TEMP_USER_ID, status, pageable);

        List<MissionResponseDto.GetMissions.GetMission> items = page.getContent().stream()
                .map(udm -> MissionResponseDto.GetMissions.GetMission.builder()
                        .title(udm.getMission().getTitle())
                        .content(udm.getMission().getContent())
                        .reward(udm.getMission().getReward())
                        .build())
                .toList();

        return MissionResponseDto.GetMissions.builder()
                .missions(items)
                .build();
    }

    public ReviewResponseDto.GetMyReviews getMyReviews() {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<Review> page = reviewRepository.findMyReviews(TEMP_USER_ID, pageable);

        List<ReviewResponseDto.GetMyReviews.ReviewItem> items = page.getContent().stream()
                .map(r -> ReviewResponseDto.GetMyReviews.ReviewItem.builder()
                        .reviewId(r.getId())
                        .nickname(r.getUser().getName())
                        .stars(r.getStars().name())
                        .content(r.getContent())
                        .createdAt(r.getCreatedAt())
                        .build())
                .toList();

        return ReviewResponseDto.GetMyReviews.builder()
                .reviews(items)
                .build();
    }

    public MissionResponseDto.GetMissionsPaged getMissions(
            Long locationId,
            Integer pageSize,
            Integer pageNumber,
            UserRequestDto.GetMissionsRequest request
    ) {

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Page<UserDoingMission> page =
                userDoingMissionRepository.findAllByUser_IdAndMission_Location_Id(
                        request.userId(), locationId, pageRequest
                );

        return UserConverter.toGetMissionsPaged(page);
    }

    public ReviewResponseDto.GetMyReviewsPaged getMyReviews(
            Long userId, Integer pageSize, String cursor, String query) {  // ← 파라미터 분리

        PageRequest pageRequest = PageRequest.of(0, pageSize);
        Slice<Review> slice;

        if (!cursor.equals("-1")) {
            String[] split = cursor.split(":");
            switch (query.toUpperCase()) {
                case "ID" -> {
                    Long idCursor = Long.parseLong(split[1]);
                    slice = reviewRepository.findReviewsByUser_IdAndIdLessThanOrderByIdDesc(
                            userId, idCursor, pageRequest
                    );
                }
                case "STARS" -> {
                    Stars starsCursor = Stars.valueOf(split[0]);
                    Long idCursor = Long.parseLong(split[1]);
                    slice = reviewRepository.findReviewsByUser_IdOrderByStarsDesc(
                            userId, starsCursor, idCursor, pageRequest
                    );
                }
                default -> throw new IllegalArgumentException("유효하지 않은 정렬 기준입니다: " + query);
            }
        } else {
            slice = switch (query.toUpperCase()) {
                case "STARS" -> reviewRepository.findReviewsByUser_IdOrderByStarsDescIdDesc(
                        userId, pageRequest
                );
                default -> reviewRepository.findReviewsByUser_IdOrderByIdDesc(
                        userId, pageRequest
                );
            };
        }

        String nextCursor = null;
        if (slice.hasNext() && !slice.getContent().isEmpty()) {
            List<Review> content = slice.getContent();
            Review last = content.get(content.size() - 1);
            nextCursor = last.getStars().name() + ":" + last.getId();
        }

        return UserConverter.toGetMyReviewsPaged(slice, nextCursor);
    }
}