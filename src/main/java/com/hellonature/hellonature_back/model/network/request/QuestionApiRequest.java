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
public class QuestionApiRequest {
    private Long idx;

    private Long memIdx;

    private Flag ansFlag;
    private String ansDate;
    private String content;
    private String ansContent;
    private String files;
    private String email;
    private String hp;
    private Integer type;

    private LocalDateTime regdate;
}
