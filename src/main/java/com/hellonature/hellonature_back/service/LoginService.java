package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Admin;
import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.LoginCheckApiRequest;
import com.hellonature.hellonature_back.model.network.response.LoginApiResponse;
import com.hellonature.hellonature_back.repository.AdminRepository;
import com.hellonature.hellonature_back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public Header<Flag> userLogin(Header<LoginCheckApiRequest> requestHeader){

        LoginCheckApiRequest request = requestHeader.getData();

        String id = request.getId();
        String password = request.getPassword();

        Optional<Member> optional = memberRepository.findByEmail(id);
        if (optional.isEmpty()) return Header.ERROR("사용자 정보가 없습니다");
        Member member = optional.get();

        if (passwordEncoder.matches(password, member.getPassword())) return Header.OK(Flag.TRUE);
        else return Header.ERROR(Flag.FALSE);
    }

    public Header<Flag> adminLogin(String id, String password){
        return adminRepository.findById(id)
                .map(admin -> Header.OK(Flag.TRUE))
                .orElseGet(() -> Header.ERROR(Flag.FALSE));
    }

    public LoginApiResponse response(Member member){
        return LoginApiResponse.builder()
                .idx(member.getIdx())
                .name(member.getName())
                .build();
    }

    public LoginApiResponse response(Admin admin){
        return LoginApiResponse.builder()
                .idx(admin.getIdx())
                .name(admin.getName())
                .build();
    }
}
