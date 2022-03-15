package com.hellonature.hellonature_back.model.network.response;

import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.enumclass.MagazineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MagazineApiResponse {

    private Long idx;

    private Flag showFlag;
    private String img;
    private String title;
    private String des;
    private String content;
    private Integer like;
    private Flag likeFlag;

    private MagazineType type;

    private String cookTime;
    private Integer cookKcal;
    private Integer cookLevel;
    private String cookIngredient;
    private LocalDateTime regdate;
}
