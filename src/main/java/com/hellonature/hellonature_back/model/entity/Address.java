package com.hellonature.hellonature_back.model.entity;

import com.hellonature.hellonature_back.model.enumclass.Flag;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "members_addresses_seq",
        sequenceName = "members_addresses_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_members_addresses")
@EqualsAndHashCode(callSuper=false)
public class Address extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "members_addresses_seq")
    private Long idx;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    private String addrName;
    @Column(name = "maddr_name")
    private String name;
    private String hp;
    private String zipcode;
    private String addr1;
    private String addr2;
    private Integer requestType;
    private String requestMemo1;
    private String requestMemo2;
    private Flag dawnFlag;
    @Builder.Default
    private Flag baseFlag = Flag.FALSE;
    @Builder.Default
    private Flag grFlag = Flag.FALSE;
}
