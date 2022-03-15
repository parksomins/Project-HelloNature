package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.NonMemberPayment;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.NonMemberPaymentApiRequest;
import com.hellonature.hellonature_back.model.network.response.MemberPaymentApiResponse;
import com.hellonature.hellonature_back.model.network.response.NonMemberPaymentApiResponse;
import com.hellonature.hellonature_back.service.MemberService;
import com.hellonature.hellonature_back.service.NonMemberPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nonmemberPayment")
@RequiredArgsConstructor
public class NonMemberPaymentApiController extends CrudController<NonMemberPaymentApiRequest, NonMemberPaymentApiResponse, NonMemberPayment> {

    private final NonMemberPaymentService nonmemberPaymentService;

    @Override
    @PostMapping("/create")
    public Header<NonMemberPaymentApiResponse> create (@RequestBody Header<NonMemberPaymentApiRequest> request) {
        return nonmemberPaymentService.create(request);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<NonMemberPaymentApiResponse> read(@PathVariable(name="idx") Long idx) {
        return nonmemberPaymentService.read(idx);
    }

    @Override
    @PutMapping("/update")
    public Header<NonMemberPaymentApiResponse> update(@RequestBody Header<NonMemberPaymentApiRequest> request) {
        return nonmemberPaymentService.update(request);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header<NonMemberPaymentApiResponse> delete(@PathVariable(name="idx") Long idx) {
        return nonmemberPaymentService.delete(idx);
    }

    @GetMapping("/list")
    public Header<List<NonMemberPaymentApiResponse>> list(@RequestParam(name="dateStart", required = false) String dateStart,
                                                       @RequestParam(name="dateEnd", required = false) String dateEnd,
                                                       @RequestParam(name="nmordIdx", required = false) Long nmordIdx,
                                                       @RequestParam(name="startPage", defaultValue = "0") Integer startPage){
        return nonmemberPaymentService.list(dateStart, dateEnd, nmordIdx, startPage);

    }

    @DeleteMapping("/deleteList/{idx}")
    public Header delete(@PathVariable("idx") List<Long> idx) {
        return nonmemberPaymentService.deletePost(idx);
    }

}
