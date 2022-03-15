package com.hellonature.hellonature_back.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheGreenApiRequest {
    private Long memIdx;
    private Integer paymentType;
    private String mpaymNum;
    private Integer type;
    private Long addrIdx;
}
