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
public class ProductQuestionListResponse {
    private Long idx;
    private Flag ansFlag;
    private String ansContent;
    private String content;
    private String name;
    private LocalDateTime regdate;

}
