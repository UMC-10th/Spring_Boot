package com.example.umc10th.global.dto;

import lombok.Builder;

@Builder
public record PageInfoDTO(
        Integer page,
        Integer size,
        Long totalElements,
        Integer totalPages
) {}