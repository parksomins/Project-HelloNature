package com.hellonature.hellonature_back.model;

import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.enumclass.MemberRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

//  = membercontext

@Getter
public class SecurityMemberLoginDTO extends User {

    private String id;
    private String password;
    private MemberRole memberRole;
    private Member member;

    public SecurityMemberLoginDTO(Member member, String id, String password, MemberRole memberRole) {
        super(id, password, getAuthorities(memberRole));
        this.member = member;
        this.id = id;
        this.password = password;
        this.memberRole = memberRole;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(MemberRole role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.getKey()));
    }
}
