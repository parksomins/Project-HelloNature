package com.hellonature.hellonature_back.model.network.request;

import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.enumclass.Gender;
import com.hellonature.hellonature_back.model.enumclass.SiteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberApiRequest {
    private  Long idx;

    private String name;
    private String email;
    private String password;
    private String hp;
    private String birth;
    private Gender gender;
    private Long mhpIdx;
    private Flag greenFlag;
    private Flag smsFlag;
    private Flag emailFlag;
    private Integer hellocash;

    private SiteType site;

    private LocalDateTime regdate;

}
