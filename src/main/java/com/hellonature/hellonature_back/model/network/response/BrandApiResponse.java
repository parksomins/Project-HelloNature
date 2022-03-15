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
public class BrandApiResponse {
    private Long idx;

    private String name;
    private String des;
    private String logo;
    private Integer state;
    private String banner;
    private String dateStart;
    private String dateEnd;
    private LocalDateTime regdate;

    private Integer proCount;
}
