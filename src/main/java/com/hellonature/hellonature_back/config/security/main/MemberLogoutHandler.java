package com.hellonature.hellonature_back.config.security.main;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberLogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getDetails() != null) {
            try {
                request.getSession().invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/user/mypage_userLogin");
    }
}
