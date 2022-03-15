package com.hellonature.hellonature_back.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    FEMALE(0, "여"), MALE(1, "남");

    private Integer id;
    private String title;
}
