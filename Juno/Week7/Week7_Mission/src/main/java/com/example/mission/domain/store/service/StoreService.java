package com.example.mission.domain.store.service;

import com.example.mission.domain.member.entity.Member;
import com.example.mission.domain.review.entity.Review;
import com.example.mission.domain.store.dto.StoreReqDTO;
import com.example.mission.domain.store.dto.StoreResDTO;
import com.example.mission.domain.store.entity.Store;
import com.example.mission.domain.store.exception.StoreException;
import com.example.mission.domain.store.exception.code.StoreErrorCode;
import com.example.mission.domain.store.repository.StoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public StoreResDTO.Review postReview(
            @PathVariable String storeId,
            @RequestHeader("Authorization") String auth,
            @RequestBody StoreReqDTO.PostReview dto
    ) {

        Store store = storeRepository.findStoreById(Long.parseLong(storeId))
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        Long memberId = parseMemberId(auth);
        Member member = memberId == null ? null : entityManager.getReference(Member.class, memberId);

        Review review = Review.builder()
                .content(dto.getContent())
                .star(5)
                .member(member)
                .store(store)
                .build();

        entityManager.persist(review);
        entityManager.flush();

        return toReviewResponse(review.getId());
    }

    private Long parseMemberId(String auth) {
        if (auth == null || auth.isBlank()) {
            return null;
        }

        String token = auth.startsWith("Bearer ") ? auth.substring(7) : auth;

        try {
            return Long.parseLong(token);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private StoreResDTO.Review toReviewResponse(Long reviewId) {
        try {
            StoreResDTO.Review response = new StoreResDTO.Review();
            Field idField = StoreResDTO.Review.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.setLong(response, reviewId == null ? 0L : reviewId);
            return response;
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to build review response", exception);
        }
    }
}
