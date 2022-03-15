package com.hellonature.hellonature_back.config.security.main;


import com.hellonature.hellonature_back.model.SecurityAdminLoginDTO;
import com.hellonature.hellonature_back.model.SecurityMemberLoginDTO;
import com.hellonature.hellonature_back.model.enumclass.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AdminSuccessHandler implements AuthenticationSuccessHandler {
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityAdminLoginDTO securityAdminLoginDTO = (SecurityAdminLoginDTO) authentication.getPrincipal();

        String url = "/admin";

        response.sendRedirect(url);
    }
}
