package com.hellonature.hellonature_back.config.security.main;

import com.hellonature.hellonature_back.model.SecurityMemberLoginDTO;
import com.hellonature.hellonature_back.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class MemberAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberDetailsService memberDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println(authentication);
        System.out.println("provider");
        String userid = authentication.getName();
        String userpw = (String)authentication.getCredentials();

        SecurityMemberLoginDTO securityMemberLoginDTO = (SecurityMemberLoginDTO) memberDetailService.loadUserByUsername(userid);
        if (securityMemberLoginDTO == null) {
            System.out.println(6666);
            throw new UsernameNotFoundException("아이디 혹은 비밀번호가 틀립니다");
        }
        String password = securityMemberLoginDTO.getPassword();

        System.out.println(password);
        System.out.println(userpw);
        System.out.println("비밀번호 검증");
        if(!passwordEncoder.matches(userpw, password)){
            System.out.println(44);
            throw new BadCredentialsException("아이디 혹은 비밀번호가 틀립니다");
        }
        System.out.println("tokenReturn");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(securityMemberLoginDTO, "hello", securityMemberLoginDTO.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
