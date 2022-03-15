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
public class NonMemberOrderApiResponse {

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


    private Long payment;
    private LocalDateTime regdate;

}
