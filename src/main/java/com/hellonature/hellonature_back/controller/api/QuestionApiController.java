package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Question;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.QuestionApiRequest;
import com.hellonature.hellonature_back.model.network.response.QuestionApiResponse;
import com.hellonature.hellonature_back.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/Question")
@RequiredArgsConstructor
public class QuestionApiController extends CrudController<QuestionApiRequest, QuestionApiResponse, Question> {
    private final QuestionService questionService;

    @RequestMapping(value = "/create" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public Header<QuestionApiResponse> create(@RequestPart(value = "key") QuestionApiRequest request,
                                              @RequestPart(value = "files", required = false) List<MultipartFile> fileList) throws Exception{
        return questionService.create(request, fileList);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<QuestionApiResponse> read(@PathVariable(name="idx") Long idx) {
        return questionService.read(idx);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = { "multipart/form-data" })
    public Header<QuestionApiResponse> update(@RequestPart(value = "key") QuestionApiRequest request,
                                              @RequestPart(value = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return questionService.update(request, fileList);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header delete(@PathVariable(name="idx") Long idx) {
        return questionService.delete(idx);
    }

    @GetMapping("/list")
    public Header<List<QuestionApiResponse>> list(@RequestParam(name="dateStart", required = false) String dateStart,
                                                  @RequestParam(name="dateEnd", required = false) String dateEnd,
                                                  @RequestParam(name="ansFlag", required = false) Flag ansFlag,
                                                  @RequestParam(name="type", required = false) Integer type,
                                                  @RequestParam(name="memEmail", required = false) String memEmail,
                                                  @RequestParam(name="page", defaultValue = "0") Integer startPage){
        return questionService.list(dateStart, dateEnd, ansFlag, type, memEmail, startPage);
    }

    @DeleteMapping("/deleteList/{idx}")
    public Header delete(@PathVariable("idx") List<Long> idx) {
        return questionService.deletePost(idx);
    }

    @GetMapping("user/list/{memIdx}")
    public Header<List<QuestionApiResponse>> myPageList(@PathVariable(name = "memIdx") Long memIdx){
        return questionService.myPageList(memIdx);
    }

    @PutMapping("ansContent/update")
    public Header<QuestionApiResponse> updateAns(@RequestBody Header<QuestionApiRequest> requestHeader){
        return questionService.updateAns(requestHeader);
    }
}
