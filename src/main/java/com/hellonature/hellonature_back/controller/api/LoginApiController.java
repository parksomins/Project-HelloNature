package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.LoginCheckApiRequest;
import com.hellonature.hellonature_back.model.network.response.LoginApiResponse;
import com.hellonature.hellonature_back.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginApiController {

    private final LoginService loginService;

//    @GetMapping("/user")
//    public Header<Flag> userLogin(@RequestParam(name = "id") String id,
//                                  @RequestParam(name = "password") String password){
//        return loginService.userLogin(id, password);
//    }

    @PostMapping("/user")
    public Header<Flag> userLogin(@RequestBody Header<LoginCheckApiRequest> requestHeader){
        return loginService.userLogin(requestHeader);
    }

    @GetMapping("/admin")
    public Header<Flag> adminLogin(@RequestParam(name = "id") String id,
                                   @RequestParam(name = "password") String password){
        return loginService.adminLogin(id, password);
    }
}
