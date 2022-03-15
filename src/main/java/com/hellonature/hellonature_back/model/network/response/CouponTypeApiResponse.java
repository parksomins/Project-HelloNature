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
public class CouponTypeApiResponse {
    private Long idx;
    private String title;
    private String target;
    private Flag auto;
    private Integer count;
    private Integer discount;
    private Integer minPrice;
    private String dateStart;
    private String dateEnd;
    private Integer type1;
    private String type2;

    private LocalDateTime regdate;

}
