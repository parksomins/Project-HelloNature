package com.hellonature.hellonature_back.model.network.response;

import com.hellonature.hellonature_back.model.enumclass.Flag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQuestionApiResponse {

    private Long idx;
    private Long memIdx;
    private String memName;
    private Long proIdx;
    private String proName;

    private String content;

    private Flag ansFlag;
    private String ansContent;
    private String ansDate;

    private String img;

    private LocalDateTime regdate;
}