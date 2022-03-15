package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Magazine;
import com.hellonature.hellonature_back.model.entity.Notice;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.NoticeApiRequest;
import com.hellonature.hellonature_back.model.network.response.FaqApiResponse;
import com.hellonature.hellonature_back.model.network.response.NoticeApiResponse;
import com.hellonature.hellonature_back.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeApiController extends CrudController<NoticeApiRequest, NoticeApiResponse, Notice> {
    private final NoticeService noticeService;

    @Override
    @PostMapping("/create")
    public Header<NoticeApiResponse> create(@RequestBody Header<NoticeApiRequest> request) {
        return noticeService.create(request);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<NoticeApiResponse> read(@PathVariable(name="idx") Long idx) {
        return noticeService.read(idx);
    }

    @Override
    @PutMapping("/update")
    public Header<NoticeApiResponse> update(@RequestBody Header<NoticeApiRequest> request) {
        return noticeService.update(request);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header<NoticeApiResponse> delete(@PathVariable(name="idx") Long idx) {
        return noticeService.delete(idx);
    }


    @GetMapping("/list")
    public Header<List<Notice>> list(@RequestParam(name="type", required = false) Integer type,
                                     @RequestParam(name="title", required = false) String title,
                                     @RequestParam(name="dateStart", required = false) String dateStart,
                                     @RequestParam(name="dateEnd", required = false) String dateEnd,
                                     @RequestParam(name="page", defaultValue = "0") Integer startPage){
        return noticeService.list(type, title, dateStart, dateEnd, startPage);
    }

    @DeleteMapping("/deleteList/{idx}")
    public Header delete(@PathVariable("idx") List<Long> idx) {
        return noticeService.deletePost(idx);
    }
}
