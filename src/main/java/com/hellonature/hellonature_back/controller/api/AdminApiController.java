package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.response.AdminMainResponse;
import com.hellonature.hellonature_back.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;

    @GetMapping("main")
    public Header<AdminMainResponse> mainPage(){
        return adminService.mainPage();
    }
}
