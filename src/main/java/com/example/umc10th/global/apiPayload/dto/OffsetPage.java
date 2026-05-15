package com.example.umc10th.global.apiPayload.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

/**
 * 오프셋 기반 페이지네이션 공통 응답 DTO.
 * Spring Data JPA의 Page<T>를 그대로 노출하지 않고
 * 프론트엔드가 사용하기 쉬운 형태로 정제해서 내려준다.
 *
 * @param <T> 실제 페이지 안에 담길 데이터 타입 (보통 응답 DTO)
 */
@Getter
@Builder
public class OffsetPage<T> {

    /** 현재 페이지의 실제 데이터 리스트 */
    private final List<T> items;

    /** 현재 페이지가 가진 아이템 수 (size와 다를 수 있음 — 마지막 페이지) */
    private final Integer listSize;

    /** 전체 페이지 수 */
    private final Integer totalPage;

    /** 전체 아이템 수 */
    private final Long totalElements;

    /** 현재 페이지 번호 (1부터 시작하도록 보정) */
    private final Integer currentPage;

    /** 첫 페이지 여부 */
    private final Boolean isFirst;

    /** 마지막 페이지 여부 */
    private final Boolean isLast;

    /**
     * Page<E> 엔티티 → OffsetPage<T> DTO로 변환하는 헬퍼.
     * 변환 함수(converter)를 받아 각 요소를 일관되게 매핑한다.
     */
    public static <E, T> OffsetPage<T> from(Page<E> page, Function<E, T> converter) {
        List<T> mapped = page.getContent().stream()
                .map(converter)
                .toList();

        return OffsetPage.<T>builder()
                .items(mapped)
                .listSize(mapped.size())
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .currentPage(page.getNumber() + 1)  // 0-based → 1-based 보정
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }
}