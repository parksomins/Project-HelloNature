package com.hellonature.hellonature_back.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SiteType {

    HELLONATURE(0, "헬로네이처"),
    KAKAO(1, "카카오"),
    NAVER(2, "네이버"),
    APPLE(3, "애플");

    private Integer id;
    private String title;
}
