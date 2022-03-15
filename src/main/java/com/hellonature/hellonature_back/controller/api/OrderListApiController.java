package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.response.MyPageOrderResponse;
import com.hellonature.hellonature_back.model.network.response.OrderListResponse;
import com.hellonature.hellonature_back.service.OrderListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orderList")
@RequiredArgsConstructor
//어드민 주문 관리 주문 조회
public class OrderListApiController {

    private final OrderListService orderListService;

    @GetMapping("")
    public Header<List<OrderListResponse>> orderList(@RequestParam(name = "dateStart", required = false) String dateStart,
                                                     @RequestParam(name = "dateEnd",required = false) String dateEnd,
                                                     @RequestParam(name = "state", required = false) Integer state,
                                                     @RequestParam(name = "member", defaultValue = "1") Integer member,
                                                     @RequestParam(name = "orderIdx", required = false) Long orderIdx,
                                                     @RequestParam(name = "startPage", defaultValue = "0") Integer startPage){
        return member == 1? orderListService.memberOrderList(dateStart, dateEnd, state, orderIdx, startPage) : orderListService.nonMemberOrderList(dateStart, dateEnd, state, orderIdx, startPage);
    }

    @GetMapping("myPage")
    public Header<List<MyPageOrderResponse>> myPage(@RequestParam(name = "dateStart") String dateStart,
                                                    @RequestParam(name = "dateEnd") String dateEnd,
                                                    @RequestParam(name = "type") Integer type,
                                                    @RequestParam(name = "memIdx") Long memIdx){
        return orderListService.myPage(dateStart, dateEnd, type, memIdx);
    }
}
