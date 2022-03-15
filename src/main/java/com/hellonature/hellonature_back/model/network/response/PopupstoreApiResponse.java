package com.hellonature.hellonature_back.model.network.response;

import com.hellonature.hellonature_back.model.entity.Brand;
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
public class PopupstoreApiResponse {

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
