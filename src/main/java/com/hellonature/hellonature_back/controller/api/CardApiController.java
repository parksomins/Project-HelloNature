package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Card;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.CardApiRequest;
import com.hellonature.hellonature_back.model.network.response.CardApiResponse;
import com.hellonature.hellonature_back.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardApiController extends CrudController<CardApiRequest, CardApiResponse, Card> {

    private final CardService cardService;

    @Override
    @PostMapping("/create")
    public Header<CardApiResponse> create(@RequestBody Header<CardApiRequest> request) {
        return cardService.create(request);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<CardApiResponse> read(@PathVariable(name = "idx") Long idx) {
        return cardService.read(idx);
    }

    @Override
    @PutMapping("/update")
    public Header<CardApiResponse> update(@RequestBody Header<CardApiRequest> request) {
        return cardService.update(request);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header delete(@PathVariable(name = "idx") Long idx) {
        return cardService.delete(idx);
    }

    @GetMapping("/list/{memIdx}")
    public Header<List<CardApiResponse>> list(@PathVariable(name = "memIdx") Long memIdx){
        return cardService.list(memIdx);
    }

    @GetMapping("/flagControl")
    public Header flagController(@RequestParam(name = "idx") Long idx,
                                 @RequestParam(name = "type") Integer type,
                                 @RequestParam(name = "flag") Flag flag){
        return cardService.flagController(idx, type, flag);
    }

    @GetMapping("changeName")
    public Header changeName(@RequestParam(name = "idx") Long idx,
                             @RequestParam(name = "name") String name){
        return cardService.changeName(idx, name);
    }

    @GetMapping("changeBaseCard")
    public Header changeBaseCard(@RequestParam(name = "idx") Long idx,
                                 @RequestParam(name = "baseIdx") Long baseIdx){
        return cardService.changeBaseCard(idx, baseIdx);
    }
}
