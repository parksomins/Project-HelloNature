package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Basket;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.BasketApiRequest;
import com.hellonature.hellonature_back.model.network.response.BasketApiResponse;
import com.hellonature.hellonature_back.model.network.response.BasketResponse;
import com.hellonature.hellonature_back.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketApiController extends CrudController<BasketApiRequest, BasketApiResponse, Basket> {

    private final BasketService basketService;

    @Override
    @PostMapping("/create")
    public Header<BasketApiResponse> create(@RequestBody Header<BasketApiRequest> request) {
        return basketService.create(request);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<BasketApiResponse> read(@PathVariable(name = "idx") Long idx) {
        return basketService.read(idx);
    }

    @Override
    @PutMapping("/update")
    public Header<BasketApiResponse> update(@RequestBody Header<BasketApiRequest> request) {
        return basketService.update(request);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header<BasketApiResponse> delete(@PathVariable(name = "idx") Long idx) {
        return basketService.delete(idx);
    }

    @GetMapping("/member/list")
    public Header<BasketResponse> memberList(@RequestParam(name = "memIdx") Long memIdx,
                                             @RequestParam(name = "proIdx", required = false) Long proIdx){
        return basketService.memberList(memIdx, proIdx);
    }

    @GetMapping("/nonMember/list")
    public Header<BasketResponse> nonMemberList(@RequestParam(name = "proList")List<Long> proList){
        return basketService.nonMemberList(proList);
    }
}
