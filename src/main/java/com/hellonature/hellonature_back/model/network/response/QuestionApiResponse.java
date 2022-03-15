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
public class QuestionApiResponse {

    private Long idx;

    private Long memIdx;
    private String name;
    private String hp;
    private String email;

    private Flag ansFlag;
    private String ansDate;
    private String title;
    private String content;
    private String ansContent;
    private String files;
    private Integer type;

    private LocalDateTime regdate;
}
