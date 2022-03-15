package com.hellonature.hellonature_back.controller.api;


import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.ProductQuestion;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.ProductQuestionApiRequest;
import com.hellonature.hellonature_back.model.network.response.MyPageProductQuestionResponse;
import com.hellonature.hellonature_back.model.network.response.ProductQuestionApiResponse;
import com.hellonature.hellonature_back.model.network.response.ProductQuestionListResponse;
import com.hellonature.hellonature_back.service.ProductQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/productquestion")
@RequiredArgsConstructor
public class ProductQuestionApiController extends CrudController<ProductQuestionApiRequest, ProductQuestionApiResponse, ProductQuestion> {
    private final ProductQuestionService productQuestionService;

    @PostMapping("create")
    public Header<ProductQuestionApiResponse> create(@RequestBody Header<ProductQuestionApiRequest> request){
        return productQuestionService.create(request);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<ProductQuestionApiResponse> read(@PathVariable(name = "idx") Long idx) {
        return productQuestionService.read(idx);
    }

    @PutMapping("update")
    public Header<ProductQuestionApiResponse> update(@RequestBody Header<ProductQuestionApiRequest> request){
        return productQuestionService.update(request);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header delete(@PathVariable(name = "idx") Long idx) {
        return productQuestionService.delete(idx);
    }

    @GetMapping("/list")
    public Header<List<ProductQuestionListResponse>> list(@RequestParam(name="typeFlag", required = false) Flag typeFlag,
                                                          @RequestParam(name="content", required = false) String content,
                                                          @RequestParam(name="name", required = false) String name,
                                                          @RequestParam(name="dateStart", required = false) String dateStart,
                                                          @RequestParam(name="dateEnd", required = false) String dateEnd,
                                                          @RequestParam(name="startPage", defaultValue = "0") Integer startPage){
        return productQuestionService.list(typeFlag, content, name, dateStart, dateEnd, startPage);

    }

    @GetMapping("/detail/list")
    public Header<List<ProductQuestionListResponse>> productDetailList(@RequestParam(name = "proIdx") Long proIdx,
                                                                       @RequestParam(name = "page", defaultValue = "0") Integer page){
        return productQuestionService.productDetailList(proIdx, page);
    }

    @GetMapping("/user/list/{memIdx}")
    public Header<List<MyPageProductQuestionResponse>> myPageList(@PathVariable(name = "memIdx") Long memIdx){
        return productQuestionService.myPageList(memIdx);
    }

    @PutMapping("ansContent/update")
    public Header<ProductQuestionApiResponse> updateAns(@RequestBody Header<ProductQuestionApiRequest> requestHeader){
        return productQuestionService.updateAns(requestHeader);
    }
}