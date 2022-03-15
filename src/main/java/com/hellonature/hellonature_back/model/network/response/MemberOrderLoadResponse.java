package com.hellonature.hellonature_back.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberOrderLoadResponse {

    private AddressApiResponse address;
    private List<MemberCouponApiResponse> couponList;
    private Integer hellocash;
}
