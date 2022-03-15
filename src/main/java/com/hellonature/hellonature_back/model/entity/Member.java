package com.hellonature.hellonature_back.model.entity;

import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.enumclass.Gender;
import com.hellonature.hellonature_back.model.enumclass.MemberRole;
import com.hellonature.hellonature_back.model.enumclass.SiteType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "members_seq",
        sequenceName = "members_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_members")
@ToString(exclude = "memberHellopass")
@EqualsAndHashCode(callSuper=false)
public class Member extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "members_seq")
    private Long idx;

    @Column(name = "mem_name")
    private String name;
    private String email;

    @Column(name = "mem_password")
    private String password;
    private String hp;
    @Nullable
    private String birth;
    @Nullable
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @OneToOne(targetEntity = MemberHellopass.class, fetch = FetchType.LAZY)
    @JoinColumn(name="mhp_idx")
    @Nullable
    private MemberHellopass memberHellopass;

    private Flag greenFlag;
    private Flag smsFlag;
    private Flag emailFlag;
    @Builder.Default
    private Integer hellocash = 0;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "mem_site")
    @Builder.Default
    private SiteType site = SiteType.HELLONATURE;

    @Builder.Default
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "member_role")
    private MemberRole role = MemberRole.MEMBER;

    public void plusHelloCash(int point){
        this.hellocash += point;
    }

    public void minusHelloCash(int point){
        this.hellocash -= point;
    }
}
