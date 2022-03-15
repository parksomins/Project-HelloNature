package com.hellonature.hellonature_back.config.security.main;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AdminLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof AuthenticationServiceException){
            request.setAttribute("loginFailMsg", "존재하지 않는 사용자입니다.");
        }
        else if(exception instanceof BadCredentialsException){
            request.setAttribute("loginFailMsg", "아이디 혹은 비밀번호가 틀립니다");
        }

        request.getRequestDispatcher("/admin/login_proc").forward(request, response);
    }
}
