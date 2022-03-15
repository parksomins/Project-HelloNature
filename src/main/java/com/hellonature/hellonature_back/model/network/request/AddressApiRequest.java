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
public class AddressApiRequest {
    private Long idx;

    private Long memIdx;
    private String addrName;
    private String name;
    private String hp;
    private String zipcode;
    private String addr1;
    private String addr2;
    private Integer requestType;
    private String requestMemo1;
    private String requestMemo2;
    private Flag dawnFlag;
    private Flag baseFlag;
    private Long baseIdx;
    private Flag greenFlag;

    private LocalDateTime regdate;
}
