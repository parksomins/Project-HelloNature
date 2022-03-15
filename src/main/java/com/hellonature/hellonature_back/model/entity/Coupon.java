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
        name = "members_coupons_seq",
        sequenceName = "members_coupons_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_members_coupons")
@EqualsAndHashCode(callSuper=false)
public class Coupon extends DateEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "members_coupons_seq")
    private Long idx;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    @ManyToOne(targetEntity = CouponType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ct_idx")
    private CouponType couponType;

    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private Flag usedFlag = Flag.FALSE;

    private String dateStart;
    private String dateEnd;
}
