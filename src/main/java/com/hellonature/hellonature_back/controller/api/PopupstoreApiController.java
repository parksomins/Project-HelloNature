package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Popupstore;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.PopupstoreApiRequest;
import com.hellonature.hellonature_back.model.network.response.PopupstoreApiResponse;
import com.hellonature.hellonature_back.service.PopupstoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/popupstore")
@RequiredArgsConstructor
public class PopupstoreApiController extends CrudController<PopupstoreApiRequest, PopupstoreApiResponse, Popupstore> {

    private final PopupstoreService popupstoreService;

    @RequestMapping(value = "/create" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public Header<PopupstoreApiResponse> create(@RequestPart(value = "key") PopupstoreApiRequest request,
                                                @RequestPart(value = "files") List<MultipartFile> fileList) throws Exception{
        return popupstoreService.create(request, fileList);
    }


    @Override
    @GetMapping("/read/{idx}")
    public Header<PopupstoreApiResponse> read(@PathVariable(name="idx") Long idx) {
        return popupstoreService.read(idx);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = { "multipart/form-data" })
    public Header<PopupstoreApiResponse> update(@RequestPart(value = "key") PopupstoreApiRequest request,
                                                @RequestPart(value = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return popupstoreService.update(request, fileList);
    }


    @Override
    @DeleteMapping("/delete/{idx}")
    public Header<PopupstoreApiResponse> delete(@PathVariable(name="idx") Long idx) {
        return popupstoreService.delete(idx);
    }

    @GetMapping("/list")
    public Header<List<Popupstore>> list(@RequestParam(name="idx", required = false) Long idx,
                                     @RequestParam(name="img", required = false) String img,
                                     @RequestParam(name = "title", required = false) String title,
                                     @RequestParam(name = "des", required = false) String des,
                                     @RequestParam(name = "brIdx", required = false) Long brIdx,
                                     @RequestParam(name = "content", required = false) String content,
                                     @RequestParam(name="dateStart", required = false) String dateStart,
                                     @RequestParam(name="dateEnd", required = false) String dateEnd,
                                     @RequestParam(name="startPage", defaultValue = "0") Integer startPage){
        return popupstoreService.list(idx, img, title, des, brIdx, content, dateStart, dateEnd, startPage);
    }

}
