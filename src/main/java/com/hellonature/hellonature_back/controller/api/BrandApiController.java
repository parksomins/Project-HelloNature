package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Brand;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.BrandApiRequest;
import com.hellonature.hellonature_back.model.network.response.BrandApiResponse;
import com.hellonature.hellonature_back.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandApiController extends CrudController<BrandApiRequest, BrandApiResponse, Brand> {
    private final BrandService brandService;

    @RequestMapping(value = "/create" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public Header<BrandApiResponse> create(@RequestPart(value = "key") BrandApiRequest request,
                                           @RequestPart(value = "files") List<MultipartFile> fileList) throws Exception{
        return brandService.create(request, fileList);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<BrandApiResponse> read(@PathVariable(name="idx") Long idx) {
        return brandService.read(idx);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = { "multipart/form-data" })
    public Header<BrandApiResponse> update(@RequestPart(value = "key") BrandApiRequest request,
                                           @RequestPart(value = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return brandService.update(request, fileList);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header<BrandApiResponse> delete(@PathVariable(name="idx") Long idx) {
        return brandService.delete(idx);
    }

    @GetMapping("/list")
    public Header<List<BrandApiResponse>> list(//@RequestParam(name="idx", required = false) Long idx,
                                               @RequestParam(name="name", required = false) String name,
                                               @RequestParam(name="state", required = false) Integer state,
                                               @RequestParam(name="dateStart", required = false) String dateStart,
                                               @RequestParam(name="dateEnd", required = false) String dateEnd,
                                               @RequestParam(name="startPage", defaultValue = "0") Integer startPage,
                                               @RequestParam(name = "count", defaultValue = "10") Integer count){
        return brandService.list(name, state, dateStart, dateEnd, startPage, count);
    }


    @GetMapping("/allList")
    public Header<List<BrandApiResponse>> allList() {
        return brandService.allList();
    }


    @DeleteMapping("/deleteList/{idx}")
    public Header delete(@PathVariable("idx") List<Long> idx) {
        return brandService.deletePost(idx);
    }
}
