package com.hellonature.hellonature_back.model.network.request;

import com.hellonature.hellonature_back.model.enumclass.Bank;
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
public class CardApiRequest {
    private Long idx;
    private Long memIdx;
    private String num;
    private String date;
    private String cvc;
    private String password;
    private Flag baseFlag;
    private String name;
    private Bank bank;
    private Flag favFlag;
    private Flag busiFlag;
    private String birth;

    private LocalDateTime regdate;

}
