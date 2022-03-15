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
public class MemberCouponApiResponse {
    private Long idx;
    private Flag usedFlag;
    private String dateStart;
    private String dateEnd;
    private LocalDateTime regdate;

    private String title;
    private Integer discount;
    private Integer minPrice;
    private Long ctIdx;
}
