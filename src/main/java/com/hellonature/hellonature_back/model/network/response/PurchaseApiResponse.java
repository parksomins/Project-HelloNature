package com.hellonature.hellonature_back.model.network.response;

import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseApiResponse {

    private Long idx;
    private Long memIdx;
    private Long proIdx;
    private String proName;
    private String proImg;
    private String proWeightSize;
    private Integer count;

    private LocalDateTime regdate;
}
