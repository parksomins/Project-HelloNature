package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.SecurityAdminLoginDTO;
import com.hellonature.hellonature_back.model.SecurityMemberLoginDTO;
import com.hellonature.hellonature_back.model.entity.Admin;
import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor //전달 받은 값이 존재함
@Service
public class AdminDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findById(username)
                .map(admin -> new SecurityAdminLoginDTO(admin.getId(), admin.getPassword(), admin.getRole()))
                .orElseGet(null);
    }
}
