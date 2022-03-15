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
public class EventApiResponse {

    private Long idx;

    private Flag typeFlag;
    private Flag ingFlag;
    private String dateStart;
    private String dateEnd;
    private String img;
    private String title;
    private String des;
    private String content;
    private LocalDateTime regdate;
}
