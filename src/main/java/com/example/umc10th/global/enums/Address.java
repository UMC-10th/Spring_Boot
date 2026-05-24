package com.example.umc10th.global.enums;

public enum Address {
    NONE("선택 안 함"),
    GANGNAM_GU("강남구"),
    GANGDONG_GU("강동구"),
    GANGBUK_GU("강북구"),
    GEUMCHEON_GU("금천구"),
    GURO_GU("구로구"),
    GWANGJIN_GU("광진구"),
    JONGNO_GU("종로구"),
    JUNG_GU("중구"),
    JUNG_GU_SEOUL("중구(서울)"),
    JUNGNANG_GU("중랑구"),
    DOBONG_GU("도봉구"),
    DONGDAEMUN_GU("동대문구"),
    DONGJAK_GU("동작구"),
    MAPO_GU("마포구"),
    NOWON_GU("노원구"),
    SEOCHO_GU("서초구"),
    SEODAEMUN_GU("서대문구"),
    SEONGBUK_GU("성북구"),
    SONGPA_GU("송파구"),
    YEONGDEUNGPO_GU("영등포구"),
    EUNPYEONG_GU("은평구"),
    ;

    private final String displayName;

    Address(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
