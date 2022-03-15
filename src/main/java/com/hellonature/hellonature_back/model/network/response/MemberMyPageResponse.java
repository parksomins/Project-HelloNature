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
public class MemberMyPageResponse {
    private Long idx;
    private String name;
    private Integer hellocash;
    private Long couponCount;
    private Flag hellopayFlag;
    private Flag hellopassFlag;
    private Flag theGreenFlag;
    private LocalDateTime regdate;
}
