package com.hellonature.hellonature_back.model.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "members_hellopass_seq",
        sequenceName = "members_hellopass_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_members_hellopass")
@EqualsAndHashCode(callSuper=false)
public class MemberHellopass extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "members_hellopass_seq")
    private Long idx;

    @OneToOne(mappedBy = "memberHellopass")
    private Member member;
    private String dateStart;
    private String dateEnd;

    @Column(name = "memhp_type")
    private Integer type;
}
