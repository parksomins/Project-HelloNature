package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Hellocash;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.HellocashApiRequest;
import com.hellonature.hellonature_back.model.network.response.HelloCashApiResponse;
import com.hellonature.hellonature_back.service.HellocashService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hellocash")
@RequiredArgsConstructor
public class HellocashApiController extends CrudController<HellocashApiRequest, HelloCashApiResponse, Hellocash> {

    private final HellocashService hellocashService;

    @GetMapping("/list")
    public Header<List<HelloCashApiResponse>> list(@RequestParam(name = "dateStart") String dateStart,
                                                   @RequestParam(name = "dateEnd") String dateEnd,
                                                   @RequestParam(name = "memIdx") Long memIdx){
        return hellocashService.list(dateStart, dateEnd, memIdx);
    }
}
