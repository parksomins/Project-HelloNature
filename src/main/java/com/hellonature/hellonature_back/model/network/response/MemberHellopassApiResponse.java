package com.hellonature.hellonature_back.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberHellopassApiResponse {

    private Long idx;

    private Long Memidx;
    private String dateStart;
    private String dateEnd;
    private Integer type;
    private LocalDateTime regdate;
}
