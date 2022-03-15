package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Event;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.EventApiRequest;
import com.hellonature.hellonature_back.model.network.response.EventApiResponse;
import com.hellonature.hellonature_back.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventApiController extends CrudController<EventApiRequest, EventApiResponse, Event> {
    private final EventService eventService;

    @RequestMapping(value = "/create" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public Header<EventApiResponse> create(@RequestPart(value = "key") EventApiRequest request,
                                           @RequestPart(value = "files") List<MultipartFile> fileList) throws Exception{
        return eventService.create(request, fileList);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<EventApiResponse> read(@PathVariable(name="idx")Long idx) {
        return eventService.read(idx);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = { "multipart/form-data" })
    public Header<EventApiResponse> update(@RequestPart(value = "key") EventApiRequest request,
                                           @RequestPart(value = "files", required = false) List<MultipartFile> fileList) throws Exception{
        return eventService.update(request, fileList);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header<EventApiResponse> delete(@PathVariable(name="idx") Long idx) {
        return eventService.delete(idx);
    }

    @GetMapping("/list")
    public Header<List<EventApiResponse>> list(@RequestParam(name="eventFlag", required = false) Flag typeFlag,
                                               @RequestParam(name="title", required = false) String title,
                                               @RequestParam(name="dateStart", required = false) String dateStart,
                                               @RequestParam(name="dateEnd", required = false) String dateEnd,
                                               @RequestParam(name = "ingFlag", required = false) Flag ingFlag,
                                               @RequestParam(name="page", defaultValue = "0") Integer page){
        return eventService.list(typeFlag, title, dateStart, dateEnd, ingFlag, page);
    }

    @GetMapping("/")
    public Header<List<EventApiResponse>> findAll(@PageableDefault(sort={"idx"}, direction= Sort.Direction.DESC) Pageable pageable) {
        return eventService.search(pageable);

    }

    @DeleteMapping("/deleteList/{idx}")
    public Header delete(@PathVariable("idx") List<Long> idx) {
        return eventService.deletePost(idx);
    }
}
