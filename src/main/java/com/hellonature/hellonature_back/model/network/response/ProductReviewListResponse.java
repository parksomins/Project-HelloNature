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
public class ProductReviewListResponse {
    private Long idx;
    private String proName;
    private Long proIdx;
    private String memName;
    private String memHp;
    private String memEmail;
    private LocalDateTime regdate;
    private String ansDate;
    private Flag ansFlag;
}
