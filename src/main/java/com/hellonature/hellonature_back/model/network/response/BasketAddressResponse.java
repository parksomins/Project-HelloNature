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
public class BasketAddressResponse {
    private Long idx;
    private String addrName;
    private String zipcode;
    private String addr1;
    private String addr2;
    private Flag dawnFlag;
    private Flag baseFlag;
    private Flag greenFlag;
    private LocalDateTime regdate;
}
