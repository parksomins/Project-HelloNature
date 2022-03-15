package com.hellonature.hellonature_back.config.security.main;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class MemberLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("fail");
        String errorMessage = "";
        if(exception instanceof UsernameNotFoundException){
            errorMessage = "존재하지 않는 사용자입니다.";
        }
        else if(exception instanceof BadCredentialsException){
            errorMessage = "아이디 혹은 비밀번호가 틀립니다";
        }
        else{
            errorMessage = "로그인 실패";
        }
        System.out.println(errorMessage);
//        request.setAttribute("errorMessage", errorMessage);
//        request.setAttribute("userid", request.getParameter("userid"));
//        request.getRequestDispatcher("/user/mypage_userLogin").forward(request, response);
        HttpSession session = request.getSession();
        session.setAttribute("userid", request.getParameter("userid"));
        session.setAttribute("errorMessage", errorMessage);
        response.sendRedirect("/user/mypage_userLogin");
    }
}