package com.hellonature.hellonature_back.model.network.request;

import com.hellonature.hellonature_back.model.enumclass.Flag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberOrderApiRequest {
    private Long idx;

    private Integer state;
    private Integer alarm;
    private String recName;
    private String recHp;
    private String zipcode;
    private String address1;
    private String address2;
    private Integer requestType;
    private String requestMemo1;
    private String requestMemo2;
    private Flag dawnFlag;
    private Flag greenFlag;
    private Integer paymentType;
    private Integer price;
    private String cardNum;

    private Long memIdx;
    private Integer hellocash;
    private List<Long> proIdxList;
    private List<Integer> proCountList;
    private Long revIdx;
    private Long cpIdx;
    private Long mpayIdx;

    private LocalDateTime regdate;
}
