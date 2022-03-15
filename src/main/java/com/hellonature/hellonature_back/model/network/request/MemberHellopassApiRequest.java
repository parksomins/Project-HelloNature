package com.hellonature.hellonature_back.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberHellopassApiRequest {
    private Long idx;

    private Long Memidx;
    private String dateStart;
    private String dateEnd;
    private Integer type;

    private LocalDateTime regdate;

}
