package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.MemberPayment;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.MemberPaymentApiRequest;
import com.hellonature.hellonature_back.model.network.response.MemberPaymentApiResponse;
import com.hellonature.hellonature_back.service.MemberPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberPayment")
@RequiredArgsConstructor
public class MemberPaymentApiController extends CrudController<MemberPaymentApiRequest, MemberPaymentApiResponse, MemberPayment> {

    private final MemberPaymentService memberPaymentService;

    @Override
    @PostMapping("/create")
    public Header<MemberPaymentApiResponse> create(@RequestBody Header<MemberPaymentApiRequest> request) {
        return memberPaymentService.create(request);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<MemberPaymentApiResponse> read(@PathVariable(name="idx") Long idx) {
        return memberPaymentService.read(idx);
    }

    @Override
    @PutMapping("/update")
    public Header<MemberPaymentApiResponse> update(@RequestBody Header<MemberPaymentApiRequest> request) {
        return memberPaymentService.update(request);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header<MemberPaymentApiResponse> delete(@PathVariable(name="idx") Long idx) {
        return memberPaymentService.delete(idx);
    }


    @GetMapping("/list")
    public Header<List<MemberPaymentApiResponse>> list(@RequestParam(name="dateStart", required = false) String dateStart,
                                                       @RequestParam(name="dateEnd", required = false) String dateEnd,
                                                       @RequestParam(name="mordIdx", required = false) Long mordIdx,
                                                       @RequestParam(name="startPage", defaultValue = "0") Integer startPage){
        return memberPaymentService.list(dateStart, dateEnd, mordIdx, startPage);

    }


    @DeleteMapping("/deleteList/{idx}")
    public void delete(@PathVariable("idx") List<Long> idx) {
        memberPaymentService.deletePost(idx);
    }
}
