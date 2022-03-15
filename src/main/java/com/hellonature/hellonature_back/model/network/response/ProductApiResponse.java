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
public class ProductApiResponse {

    private Long idx;

    private String name;
    private String des;
    private Integer netPrice;
    private Integer salePrice;
    private Integer price;
    private Integer state;
    private String dateStart;
    private String dateEnd;
    private String origin;
    private String sizeWeight;
    private Integer temp;
    private Integer count;
    private Integer delivery;
    private Integer packing;
    private String img1, img2, img3, img4;
    private String proDes;
    private Integer like;
    private Flag bestFlag;
    private Flag likeFlag;
    private String proType;
    private String proName;
    private String foodType;
    private String producer;
    private String location;
    private String dateBuilt;
    private String dateValid;

    private Long brIdx;
    private Long cateIdx;
    private Long eveCateIdx;

    private String cateName;

    private LocalDateTime regdate;
}