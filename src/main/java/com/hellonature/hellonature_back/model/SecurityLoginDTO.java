package com.hellonature.hellonature_back.model;

import com.hellonature.hellonature_back.model.enumclass.MemberRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

@Getter
public class SecurityLoginDTO extends User {

    private String id;
    private String password;
    private MemberRole memberRole;

    public SecurityLoginDTO(String id, String password, MemberRole memberRole) {
        super(id, password, getAuthorities(memberRole));
        this.id = id;
        this.password = password;
        this.memberRole = memberRole;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(MemberRole role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.getKey()));
    }
}
