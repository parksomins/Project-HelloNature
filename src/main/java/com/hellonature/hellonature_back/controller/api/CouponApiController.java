package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Coupon;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.CouponApiRequest;
import com.hellonature.hellonature_back.model.network.response.CouponApiResponse;
import com.hellonature.hellonature_back.model.network.response.MemberCouponApiResponse;
import com.hellonature.hellonature_back.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberCoupon")
@RequiredArgsConstructor
public class CouponApiController extends CrudController<CouponApiRequest, CouponApiResponse, Coupon> {

    private final CouponService couponService;

    @Override
    @PostMapping("/create")
    public Header<CouponApiResponse> create(@RequestBody Header<CouponApiRequest> request) {
        return couponService.create(request);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<CouponApiResponse> read(@PathVariable(name = "idx") Long idx) {
        return couponService.read(idx);
    }

    @Override
    @PutMapping("/update")
    public Header<CouponApiResponse> update(@RequestBody Header<CouponApiRequest> request) {
        return couponService.update(request);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header delete(@PathVariable(name = "idx") Long idx) {
        return couponService.delete(idx);
    }

    @GetMapping("/count/{memIdx}")
    public Header<Long> count(@PathVariable(name = "memIdx") Long memIdx){
        return couponService.count(memIdx);
    }

    @GetMapping("/list")
    public Header<List<MemberCouponApiResponse>> list(@RequestParam(name = "memIdx") Long memIdx,
                                                      @RequestParam(name = "usedFlag")Flag usedFlag){
        return couponService.list(memIdx, usedFlag);
    }
}
