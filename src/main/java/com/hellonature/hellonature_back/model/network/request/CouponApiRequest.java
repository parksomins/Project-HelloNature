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
public class CouponApiRequest {
    private Long idx;

    private Long memIdx;
    private Long ctIdx;
    private Flag usedFlag;

    private String dateStart;
    private String dateEnd;

    private LocalDateTime regdate;
}
