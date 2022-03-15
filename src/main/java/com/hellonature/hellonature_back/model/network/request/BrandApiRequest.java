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
public class BrandApiRequest {
    private Long idx;
    private String name;
    private String des;
    private String logo;
    private Integer state;
    private String banner;
    private String dateStart;
    private String dateEnd;

    private LocalDateTime regdate;

}
