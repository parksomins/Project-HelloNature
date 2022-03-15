package com.hellonature.hellonature_back.config.security.main;

import com.hellonature.hellonature_back.model.SecurityMemberLoginDTO;
import com.hellonature.hellonature_back.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
@Component

public class MemberSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private MemberDetailsService memberDetailService;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        String userid = authentication.getName();

        SecurityMemberLoginDTO securityMemberLoginDTO = (SecurityMemberLoginDTO) memberDetailService.loadUserByUsername(userid);
        System.out.println(securityMemberLoginDTO.getMember().getIdx());

        String url = "/user/index";

        HttpSession session = request.getSession();
        session.setAttribute("idx", securityMemberLoginDTO.getMember().getIdx());
        session.setAttribute("name", securityMemberLoginDTO.getMember().getName());
        session.setAttribute("email", securityMemberLoginDTO.getMember().getEmail());
        session.setAttribute("hp", securityMemberLoginDTO.getMember().getHp());
        session.setAttribute("regdate", securityMemberLoginDTO.getMember().getRegdate());
        if (session.getAttribute("errorMessage") != null) {
            session.removeAttribute("userid");
            session.removeAttribute("errorMessage");
        }

        response.sendRedirect(url);
    }
}
