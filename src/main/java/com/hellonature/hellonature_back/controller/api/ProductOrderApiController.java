package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.MemberOrderApiRequest;
import com.hellonature.hellonature_back.model.network.request.NonMemberOrderApiRequest;
import com.hellonature.hellonature_back.model.network.response.MemberOrderApiResponse;
import com.hellonature.hellonature_back.model.network.response.NonMemberOrderApiResponse;
import com.hellonature.hellonature_back.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productOrder")
@RequiredArgsConstructor
//상품 주문 API
public class ProductOrderApiController {

    private final ProductOrderService productOrderService;

    @PostMapping("member/create")
    public Header<MemberOrderApiResponse> memberOrder(@RequestBody Header<MemberOrderApiRequest> request){
        return productOrderService.memberOrder(request);
    }

    @PostMapping("nonmember/create")
    public Header<NonMemberOrderApiResponse> nonMemberOrder(@RequestBody Header<NonMemberOrderApiRequest> request){
        return productOrderService.nonMemberOrder(request);
    }
}
