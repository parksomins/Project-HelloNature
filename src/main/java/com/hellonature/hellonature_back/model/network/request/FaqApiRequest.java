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
public class FaqApiRequest {
    private Long idx;
    private Integer type;
    private String subject;
    private String title;
    private String content;

    private LocalDateTime regdate;

}
