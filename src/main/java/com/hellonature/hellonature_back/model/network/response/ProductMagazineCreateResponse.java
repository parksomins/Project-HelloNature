package com.hellonature.hellonature_back.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMagazineCreateResponse {

    private List<Long> idx;
    private List<String> name;
    private List<String> img;
    private List<String> cateName;
    private List<Long> idxList;
    private List<String> nameList;
    private List<String> imgList;

    public void addIdx(Long idx){
        this.idx.add(idx);
    }

    public void addName(String name){
        this.name.add(name);
    }

    public void addImg(String img){
        this.img.add(img);
    }

    public void addCateName(String cateName){
        this.cateName.add(cateName);
    }
}
