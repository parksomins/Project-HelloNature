package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Category;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.CategoryApiRequest;
import com.hellonature.hellonature_back.model.network.response.CategoryApiResponse;
import com.hellonature.hellonature_back.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryApiController extends CrudController<CategoryApiRequest, CategoryApiResponse, Category> {

    private final CategoryService categoryService;

    @Override
    @PostMapping("/create")
    public Header<CategoryApiResponse> create(@RequestBody Header<CategoryApiRequest> request) {
        return categoryService.create(request);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<CategoryApiResponse> read(@PathVariable(name = "idx") Long idx) {
        return categoryService.read(idx);
    }

    @RequestMapping(value ="/update", method = RequestMethod.PUT, consumes = { "multipart/form-data"})
    public Header<CategoryApiResponse> update(@RequestBody Header<CategoryApiRequest> request,
                                              @RequestPart(value = "files") List<MultipartFile> fileList) throws Exception  {
        return categoryService.update(request, fileList);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header<CategoryApiResponse> delete(@PathVariable(name = "idx") Long idx) {
        return categoryService.delete(idx);
    }

    @GetMapping("/list/first")
    public Header<List<CategoryApiResponse>> first(@RequestParam(name = "lifeFlag") Flag lifeFlag){
        return categoryService.first(lifeFlag);
    }

    @GetMapping("/list/second")
    public Header<List<CategoryApiResponse>> second(@RequestParam(name = "idx") Long idx,
                                                    @RequestParam(name = "lifeFlag") Flag lifeFlag){
        return categoryService.second(idx, lifeFlag);
    }
}