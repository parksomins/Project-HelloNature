package com.hellonature.hellonature_back.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDailyResponse {

    private Long product1;
    private Long product2;
    private Long product3;

    private Long brand1;
    private Long brand2;

    private Long productQuestion1;
    private Long productQuestion2;

    private Long question1;

    private Long magazine1;
    private Long magazine2;

    private Long order1;
    private Long order2;
    private Long order3;
    private Long order4;
}
