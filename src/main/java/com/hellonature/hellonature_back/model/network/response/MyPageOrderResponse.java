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
public class MyPageOrderResponse {
    private Long proIdx;
    private String proName;
    private String proImg;
    private String proWeightSize;
    private Integer proCount;
    private Integer state;
    private LocalDateTime regdate;
    private String recName;
    private String recHp;
    private String zipcode;
    private String address1;
    private String address2;
    private Flag greenFlag;
    private Flag dawnFlag;
    private Long rvIdx;
    private Flag ansFlag;
}
