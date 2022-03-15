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
public class PopupstoreApiRequest {
    private Long idx;
    private String img;
    private String title;
    private String des;

    private Long brIdx;

    private String content;
    private String dateStart;
    private String dateEnd;

    private LocalDateTime regdate;

}
