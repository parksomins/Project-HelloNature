package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Product;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.ProductApiRequest;
import com.hellonature.hellonature_back.model.network.response.ProductApiResponse;
import com.hellonature.hellonature_back.model.network.response.ProductDetailResponse;
import com.hellonature.hellonature_back.model.network.response.ProductMagazineCreateResponse;
import com.hellonature.hellonature_back.model.network.response.ProductUserListResponse;
import com.hellonature.hellonature_back.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductApiController extends CrudController<ProductApiRequest, ProductApiResponse, Product> {

    private final ProductService productService;

    @RequestMapping(value = "/create" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public Header<ProductApiResponse> create(@RequestPart(value = "key") ProductApiRequest request,
                                             @RequestPart(value = "files") List<MultipartFile> fileList) throws Exception{
        return productService.create(request, fileList);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<ProductApiResponse> read(@PathVariable(name = "idx") Long idx) {
        return productService.read(idx);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT, consumes = { "multipart/form-data" })
    public Header<ProductApiResponse> update(@RequestPart(value = "key") ProductApiRequest request,
                                             @RequestPart(value = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return productService.update(request, fileList);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header<ProductApiResponse> delete(@PathVariable(name = "idx") Long idx) {
        return productService.delete(idx);
    }

    @DeleteMapping("deleteList/{idxList}")
    public Header deleteList(@PathVariable(name = "idxList") List<Long> idxList){
        return productService.deleteList(idxList);
    }

    @GetMapping("list")
    public Header<List<ProductApiResponse>> list(@RequestParam(name = "state", required = false) Integer state,
                                                 @RequestParam(name = "cateIdx", required = false) Long cateIdx,
                                                 @RequestParam(name = "name", required = false) String name,
                                                 @RequestParam(name = "idx", required = false) Long idx,
                                                 @RequestParam(name = "brIdx", required = false) Long brIdx,
                                                 @RequestParam(name = "order", defaultValue = "1") Integer order,
                                                 @RequestParam(name = "page", defaultValue = "0") Integer page){
        return productService.list(state, cateIdx, name, idx, brIdx, order, page);
    }

    @GetMapping("magazineProduct")
    public Header<ProductMagazineCreateResponse> magazineProduct(@RequestParam(name = "cateIdx", required = false) Long cateIdx,
                                                                 @RequestParam(name = "state", required = false) Integer state,
                                                                 @RequestParam(name = "order", required = false) Integer order,
                                                                 @RequestParam(name = "proName", required = false) String proName,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "idxList", required = false) List<Long> idxList,
                                                                 @RequestParam(name = "nameList", required = false) List<String> nameList,
                                                                 @RequestParam(name = "imgList", required = false) List<String> imgList){
        return productService.magazineProduct(cateIdx, state, order, proName, page, idxList, nameList, imgList);
    }

    @GetMapping("user/list")
    public Header<List<ProductUserListResponse>> userList(@RequestParam(name = "cateIdx", required = false) Long cateIdx,
                                                          @RequestParam(name = "brIdx", required = false) Long brIdx,
                                                          @RequestParam(name = "saleFlag", required = false) Flag saleFlag,
                                                          @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(name = "order", defaultValue = "1") Integer order){
        return productService.userList(cateIdx, brIdx,saleFlag, page, order);
    }

    @GetMapping("user/list/newProduct")
    public Header<List<ProductUserListResponse>> newProductList(@RequestParam(name = "date") String date,
                                                                @RequestParam(name = "page") Integer page,
                                                                @RequestParam(name = "order", defaultValue = "1") Integer order){
        return productService.newProductList(date, page, order);
    }

    @GetMapping("user/list/saleProduct")
    public Header<List<ProductUserListResponse>> saleProductList(@RequestParam(name = "page") Integer page,
                                                                 @RequestParam(name = "order", defaultValue = "1") Integer order){
        return productService.saleProductList(page, order);
    }

    @GetMapping("user/list/productList")
    public Header<List<ProductUserListResponse>> productList(@RequestParam(name = "proList") List<Long> proList){
        return productService.productList(proList);
    }

    @GetMapping("detail")
    public Header<ProductDetailResponse> detail(@RequestParam(name = "proIdx") Long proIdx,
                                                @RequestParam(name = "memIdx", required = false) Long memIdx){
        return productService.detail(proIdx, memIdx);
    }
}
