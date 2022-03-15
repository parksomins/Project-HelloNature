package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Like;
import com.hellonature.hellonature_back.model.entity.Magazine;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.LikeApiRequest;
import com.hellonature.hellonature_back.model.network.response.LikeApiResponse;
import com.hellonature.hellonature_back.model.network.response.MagazineApiResponse;
import com.hellonature.hellonature_back.model.network.response.ProductApiResponse;
import com.hellonature.hellonature_back.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberslikes")
@RequiredArgsConstructor
public class LikeApiController{

    private final LikeService likeService;

    @PostMapping("/create")
    public Header<LikeApiResponse> create(@RequestBody Header<LikeApiRequest> request) {
        return likeService.create(request);
    }

    @DeleteMapping("/delete")
    public Header delete(@RequestParam(name = "memIdx") Long memIdx,
                         @RequestParam(name = "proIdx", required = false) Long proIdx,
                         @RequestParam(name = "magIdx", required = false) Long magIdx) {
        return proIdx == null ? likeService.magDelete(memIdx, magIdx) : likeService.proDelete(memIdx, proIdx);
    }

    @GetMapping("/list")
    public Header<List<LikeApiResponse>> likeList(@RequestParam(name = "type") Integer type,
                                                  @RequestParam(name = "memIdx") Long memIdx){
        return type == 1 ? likeService.productList(memIdx) : likeService.magazineList(memIdx);
    }

}
