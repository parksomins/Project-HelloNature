package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.dto.SendMailDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendMailService {
    @Autowired
    private JavaMailSender mailSender;

    private static final String FROM_ADDRESS = "korpg95274@gmail.com";

    public SendMailDTO createMail(String userEmail){

        String str = getTempPassword();
        SendMailDTO sendMailDTO = new SendMailDTO();
        sendMailDTO.setAddress(userEmail);
        sendMailDTO.setTitle("회원님의 헬로네이처 이메일 인증 관련 메일입니다.");
        sendMailDTO.setMessage("안녕하세요. 아래 번호를 브라우저 창에 입력해주세요. 인증번호는 " + str + " 입니다.");
        sendMailDTO.setCertification(str);
        return sendMailDTO;
    }

    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 4; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    public void mailSend(SendMailDTO sendMailDTO){

        System.out.println("이멜 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendMailDTO.getAddress());
        message.setFrom(SendMailService.FROM_ADDRESS);
        message.setSubject(sendMailDTO.getTitle());
        message.setText(sendMailDTO.getMessage());
        mailSender.send(message);
    }
}
