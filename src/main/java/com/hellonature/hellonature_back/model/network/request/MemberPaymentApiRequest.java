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
public class MemberPaymentApiRequest {
    private Long idx;

    private Long mordIdx;
    private Integer price;
    private Integer state;
    private Integer paymentType;
    private String num;

    private LocalDateTime regdate;

}
