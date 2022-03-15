package com.hellonature.hellonature_back.model.entity;

import com.hellonature.hellonature_back.model.enumclass.MemberRole;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "admins_seq",
        sequenceName = "admins_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_admins")
@EqualsAndHashCode(callSuper=false)
public class Admin extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admins_seq")
    private Long idx;

    @Column(name = "ad_name")
    private String name;
    @Column(name = "ad_id")
    private String id;
    @Column(name = "ad_password")
    private String password;

    @Builder.Default
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "member_role")
    private MemberRole role = MemberRole.ADMIN;
}
