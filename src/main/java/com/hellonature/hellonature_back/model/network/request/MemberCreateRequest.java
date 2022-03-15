package com.hellonature.hellonature_back.model.network.request;

import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.enumclass.Gender;
import com.hellonature.hellonature_back.model.enumclass.SiteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberCreateRequest {
    private String name;
    private String email;
    private String password;
    private String hp;
    private String birth;
    private Gender gender;
    private Flag smsFlag;
    private Flag emailFlag;

    private String addrName;
    private String zipcode;
    private String addr1;
    private String addr2;
    private Integer requestType;
    private String requestMemo1;
    private String requestMemo2;

    private SiteType site;

}
