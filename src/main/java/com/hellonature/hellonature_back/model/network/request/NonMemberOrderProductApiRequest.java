package com.hellonature.hellonature_back.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NonMemberOrderProductApiRequest {
    private Long idx;

    private Long nmemIdx;
    private Long proIdx;

    private Integer proCount;
    private Integer proPrice;

    private LocalDateTime regdate;

}
