package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.entity.Address;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.AddressApiRequest;
import com.hellonature.hellonature_back.model.network.response.AddressApiResponse;
import com.hellonature.hellonature_back.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membersAddresses")
@RequiredArgsConstructor
public class AddressApiController extends CrudController<AddressApiRequest, AddressApiResponse, Address> {

    private final AddressService addressService;

    @Override
    @PostMapping("/create")
    public Header<AddressApiResponse> create(@RequestBody Header<AddressApiRequest> request) {
        return addressService.create(request);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<AddressApiResponse> read(@PathVariable(name="idx") Long idx) {
        return addressService.read(idx);
    }

    @Override
    @PutMapping("/update")
    public Header<AddressApiResponse> update(@RequestBody Header<AddressApiRequest> request) {
        return addressService.update(request);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header<AddressApiResponse> delete(@PathVariable(name="idx") Long idx) {
        return addressService.delete(idx);
    }

    @GetMapping("/list")
    public Header<List<AddressApiResponse>> list(@RequestParam(name="name", required = false) String name,
                                                 @RequestParam(name="startPage", defaultValue = "0") Integer startPage){
        return addressService.list(name, startPage);
    }

    @DeleteMapping("/deleteList/{idx}")
    public Header delete(@PathVariable("idx") List<Long> idx) {
        return addressService.deletePost(idx);
    }

    @GetMapping("user/list/{memIdx}")
    public Header<List<AddressApiResponse>> userList(@PathVariable(name = "memIdx") Long idx){
        return addressService.userList(idx);
    }
}
