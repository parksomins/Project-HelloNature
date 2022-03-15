package com.hellonature.hellonature_back.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Flag {

    FALSE(0, false), TRUE(1, true);

    private Integer id;
    private boolean title;
}
