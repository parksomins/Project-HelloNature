package com.hellonature.hellonature_back.model.network.request;

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
public class ProductReviewApiRequest {
    private Long idx;

    private Long memIdx;
    private Long proIdx;

    private Integer like;
    private String content;
    private Flag ansFlag;
    private String ansContent;
    private String files;
    private String ansDate;

    private LocalDateTime regdate;

}
