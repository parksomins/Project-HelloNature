package com.hellonature.hellonature_back.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MagazineType {

    RECIPE(0, "레시피"),
    NOTE(1, "탐험노트"),
    STYLE(2, "라이프스타일"),
    GUIDE(3, "키친가이드");

    private Integer id;
    private String title;
}