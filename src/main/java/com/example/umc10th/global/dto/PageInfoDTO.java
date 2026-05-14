package com.example.umc10th.global.dto;

import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record PageInfoDTO(
        Integer page,
        Integer size,
        Long totalElements,
        Integer totalPages
) {
    public static PageInfoDTO from(Page<?> page) {
        return PageInfoDTO.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}