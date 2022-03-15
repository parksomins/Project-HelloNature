package com.hellonature.hellonature_back.model.dto;

import com.hellonature.hellonature_back.model.enumclass.Flag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowProduct{
    private Long idx;
    private String name;
    private String img;
    private Integer netPrice;
    private Integer salePrice;
    private Flag bestFlag;
}