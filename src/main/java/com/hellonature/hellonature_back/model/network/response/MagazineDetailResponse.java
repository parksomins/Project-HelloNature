package com.hellonature.hellonature_back.model.network.response;

import com.hellonature.hellonature_back.model.dto.ShowProduct;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.enumclass.MagazineType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MagazineDetailResponse {
    private Long idx;

    private String img;
    private String title;
    private String des;
    private String content;
    private Long like;
    private Flag likeFlag;

    private MagazineType type;

    private String cookTime;
    private Integer cookKcal;
    private Integer cookLevel;
    private String cookIngredient;
    @Builder.Default
    private List<ShowProduct> ingreList = new ArrayList<>();
    @Builder.Default
    private List<ShowProduct> relList = new ArrayList<>();

    private LocalDateTime regdate;

    public void addIngreList(Long idx, String name, String img, Integer netprice, Integer salePrice, Flag bestFlag){
        ingreList.add(new ShowProduct(idx, name, img, netprice, salePrice, bestFlag));
    }

    public void addRelList(Long idx, String name, String img, Integer netprice, Integer salePrice, Flag bestFlag){
        relList.add(new ShowProduct(idx, name, img, netprice, salePrice, bestFlag));
    }
}