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
public class NonMemberOrderApiRequest {
    private Long idx;

    private Integer state;
    private String name;
    private String hp;
    private Flag dawnFlag;
    private Integer alarm;
    private String zipcode;
    private String address1;
    private String address2;
    private Integer requestType;
    private String requestMemo1;
    private String requestMemo2;
    private Integer price;
    private Integer paymentType;
    private String num;

    private Long[] proIdx;
    private Integer[] proCount;
    private Integer[] proPrice;

    private Long nmpayIdx;

    private LocalDateTime regdate;

}
