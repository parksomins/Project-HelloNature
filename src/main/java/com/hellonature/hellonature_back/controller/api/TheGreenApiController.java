package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.TheGreenApiRequest;
import com.hellonature.hellonature_back.service.TheGreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/theGreen")
@RequiredArgsConstructor
public class TheGreenApiController {

    private final TheGreenService theGreenService;

    @PostMapping("create")
    public Header create(@RequestBody Header<TheGreenApiRequest> theGreenApiRequest){
        return theGreenService.create(theGreenApiRequest);
    }

    @DeleteMapping("delete/{memIdx}")
    public Header delete(@PathVariable(name = "memIdx") Long memIdx){
        return theGreenService.delete(memIdx);
    }

    @GetMapping("check/{memIdx}")
    public Header<Flag> check(@PathVariable(name = "memIdx") Long memIdx){
        return theGreenService.check(memIdx);
    }
}
