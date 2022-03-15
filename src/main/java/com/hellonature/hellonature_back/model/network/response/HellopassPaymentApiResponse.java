package com.hellonature.hellonature_back.model.network.response;

import com.hellonature.hellonature_back.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HellopassPaymentApiResponse {
    private Long idx;

    private Integer type;
    private Integer price;

    private Long member;
    private String cardNum;
    private LocalDateTime regdate;
}
