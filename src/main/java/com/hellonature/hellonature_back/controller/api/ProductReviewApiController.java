package com.hellonature.hellonature_back.controller.api;


import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.ProductReview;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.ProductReviewApiRequest;
import com.hellonature.hellonature_back.model.network.response.MyPageOrderResponse;
import com.hellonature.hellonature_back.model.network.response.ProductReviewApiResponse;
import com.hellonature.hellonature_back.model.network.response.ProductReviewListResponse;
import com.hellonature.hellonature_back.model.network.response.MemberReviewResponse;
import com.hellonature.hellonature_back.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/productReview")
@RequiredArgsConstructor
public class ProductReviewApiController extends CrudController<ProductReviewApiRequest, ProductReviewApiResponse, ProductReview> {
    private final ProductReviewService productReviewService;

    @RequestMapping(value = "/create" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public Header<ProductReviewApiResponse> create(@RequestPart(value = "key") ProductReviewApiRequest request,
                                                   @RequestPart(value = "files") List<MultipartFile> fileList) throws Exception{
        return productReviewService.create(request,fileList);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<ProductReviewApiResponse> read(@PathVariable(name="idx") Long idx) {
        return productReviewService.read(idx);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = { "multipart/form-data" })
    public Header<ProductReviewApiResponse> update(@RequestPart(value = "key") ProductReviewApiRequest request,
                                                   @RequestPart(value = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return productReviewService.update(request, fileList);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header delete(@PathVariable(name="idx") Long idx) {
        return productReviewService.delete(idx);
    }

    @GetMapping("/list")
    public Header<List<ProductReviewListResponse>> list(@RequestParam(name="ansFlag", required = false) Flag ansFlag,
                                                        @RequestParam(name="proIdx", required = false) Long proIdx,
                                                        @RequestParam(name="startPage", defaultValue = "0") Integer page){
        return productReviewService.list(ansFlag, proIdx, page);
    }

    @GetMapping("userReview/list")
    public Header<List<MemberReviewResponse>> userReviewList(@RequestParam(name="proIdx") Long proIdx,
                                                             @RequestParam(name="like", required = false) Integer like,
                                                             @RequestParam(name="page", defaultValue = "0") Integer page){
        return productReviewService.userReviewList(proIdx, like, page);
    }

    @GetMapping("list/myPage")
    public Header<List<MyPageOrderResponse>> myPage(@RequestParam(name = "flag") Flag flag,
                                                    @RequestParam(name = "memIdx") Long idx,
                                                    @RequestParam(name = "dateStart") String dateStart,
                                                    @RequestParam(name = "dateEnd") String dateEnd){
        return productReviewService.myPage(flag, idx, dateStart, dateEnd);
    }

    @PutMapping("ansContent/update")
    public Header updateAns(@RequestBody Header<ProductReviewApiRequest> request){
        return productReviewService.updateAns(request);
    }
}
