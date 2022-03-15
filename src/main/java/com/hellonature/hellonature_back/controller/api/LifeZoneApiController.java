package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Product;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.ProductApiRequest;
import com.hellonature.hellonature_back.model.network.response.ProductApiResponse;
import com.hellonature.hellonature_back.service.LifeZoneService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lifezone")
@RequiredArgsConstructor
public class LifeZoneApiController extends CrudController<ProductApiRequest, ProductApiResponse, Product> {

    private final LifeZoneService lifeZoneService;

    @GetMapping("mainList")
    public Header<List<ProductApiResponse>> mainList(@PageableDefault(sort={"idx"}, direction= Sort.Direction.DESC) Pageable pageable) {
        return lifeZoneService.search(pageable);
    }

    @GetMapping("productList/{eveCateIdx}")
    public Header<List<ProductApiResponse>> productList(@PathVariable(name = "eveCateIdx") Long eveCateIdx){
        return lifeZoneService.productList(eveCateIdx);
    }

    @GetMapping("deleteList/{proList}")
    public Header deleteList(@PathVariable(name = "proList") List<Long> proList){
        return lifeZoneService.deleteList(proList);
    }
}