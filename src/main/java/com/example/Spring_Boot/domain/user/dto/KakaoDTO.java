package com.example.Spring_Boot.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoDTO implements OAuthDTO {
    private String socialUid;
    private String email;
    private String name;
}