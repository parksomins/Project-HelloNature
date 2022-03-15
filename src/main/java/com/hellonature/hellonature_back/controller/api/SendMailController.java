package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.model.dto.SendMailDTO;
import com.hellonature.hellonature_back.service.MemberService;
import com.hellonature.hellonature_back.service.SendMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SendMailController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private SendMailService sendMailService;

    @GetMapping("/check/findPw")
    public @ResponseBody
    Map<String, Boolean> pw_find(String userEmail){
        Map<String,Boolean> json = new HashMap<>();
        boolean pwFindCheck = memberService.memberEmailcheck(userEmail);

        json.put("check", pwFindCheck);
        return json;
    }

    @PostMapping("/check/findPw/sendEmail")
    public @ResponseBody String sendEmail(String userEmail){
        SendMailDTO sendMailDTO = sendMailService.createMail(userEmail);
        sendMailService.mailSend(sendMailDTO);
        return sendMailDTO.getCertification();
    }
}
