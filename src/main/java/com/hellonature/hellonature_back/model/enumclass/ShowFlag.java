package com.hellonature.hellonature_back.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShowFlag {
    HIDDEN(0, "0"),
    SHOW(1, "1");

    private Integer id;
    private String title;
}
