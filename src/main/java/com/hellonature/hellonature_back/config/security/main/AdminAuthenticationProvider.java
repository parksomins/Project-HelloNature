package com.hellonature.hellonature_back.config.security.main;


import com.hellonature.hellonature_back.model.SecurityAdminLoginDTO;
import com.hellonature.hellonature_back.service.AdminDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Component
@RequiredArgsConstructor
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AdminDetailsService adminDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userid = authentication.getName();
        String userpw = (String)authentication.getCredentials();

        SecurityAdminLoginDTO securityAdminLoginDTO = (SecurityAdminLoginDTO) adminDetailsService.loadUserByUsername(userid);
        if (securityAdminLoginDTO == null ) throw new BadCredentialsException("비밀번호가 틀립니다");
        String password = securityAdminLoginDTO.getPassword();

        if(!passwordEncoder.matches(userpw, password)){
            throw new BadCredentialsException("비밀번호가 틀립니다");
        }
        return new UsernamePasswordAuthenticationToken(securityAdminLoginDTO, null, securityAdminLoginDTO.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
