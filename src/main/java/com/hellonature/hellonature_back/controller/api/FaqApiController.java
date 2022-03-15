package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Faq;

import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.FaqApiRequest;

import com.hellonature.hellonature_back.model.network.response.FaqApiResponse;

import com.hellonature.hellonature_back.service.FaqService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
public class FaqApiController extends CrudController<FaqApiRequest, FaqApiResponse, Faq> {
    private final FaqService faqService;

    @Override
    @PostMapping("/create")
    public Header<FaqApiResponse> create(@RequestBody Header<FaqApiRequest> request) {
        return faqService.create(request);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<FaqApiResponse> read(@PathVariable(name="idx") Long idx) {
        return faqService.read(idx);
    }

    @Override
    @PutMapping("/update")
    public Header<FaqApiResponse> update(@RequestBody Header<FaqApiRequest> request) {
        return faqService.update(request);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header<FaqApiResponse> delete(@PathVariable(name="idx") Long idx) {
        return faqService.delete(idx);
    }


    @GetMapping("/list")
    public Header<List<FaqApiResponse>> list(@RequestParam(name="type", required = false) Integer type,
                                             @RequestParam(name="subject", required = false) String subject,
                                             @RequestParam(name="title", required = false) String title,
                                             @RequestParam(name="content", required = false) String content,
                                             @RequestParam(name="page", defaultValue = "0") Integer startPage){
        return faqService.list(type, subject, title, content, startPage);
    }

    @Transactional
    @DeleteMapping("/deleteList/{idx}")
    public Header delete(@PathVariable("idx") List<Long> idx) {
        return faqService.deletePost(idx);
    }

    @GetMapping("user/list/{type}")
    public Header<List<FaqApiResponse>> userList(@PathVariable(name = "type") Integer type){
        return faqService.userList(type);
    }

}