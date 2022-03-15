package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Purchase;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.PurchaseApiRequest;
import com.hellonature.hellonature_back.model.network.response.PurchaseApiResponse;
import com.hellonature.hellonature_back.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/frequentlyOrder")
@RequiredArgsConstructor
public class PurchaseApiController extends CrudController<PurchaseApiRequest, PurchaseApiResponse, Purchase> {
    private final PurchaseService purchaseService;

    @GetMapping("/list")
    public Header<List<PurchaseApiResponse>> list(@RequestParam(name = "memIdx") Long memIdx,
                                                  @RequestParam(name = "dateStart") String dateStart,
                                                  @RequestParam(name = "dateEnd") String dateEnd){
        return purchaseService.list(memIdx, dateStart, dateEnd);
    }
}
