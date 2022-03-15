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
public class ProductUserListResponse {
    private Long idx;
    private String name;
    private String img;
    private Integer netPrice;
    private Integer salePrice;
    private Integer price;
    private Integer temp;
    private Integer count;
    private Float star;
    private Flag bestFlag;
    private LocalDateTime regdate;
}
