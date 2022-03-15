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
public class HellopassPaymentApiRequest {
    private Long idx;

    private Integer type;
    private Integer price;
    private Long memIdx;
    private String cardNum;

    private LocalDateTime regdate;

}
