package com.hellonature.hellonature_back.model.entity;

import com.hellonature.hellonature_back.model.enumclass.Flag;

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
        name = "members_orders_seq",
        sequenceName = "members_orders_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_members_orders")
@ToString(exclude = "payment")
@EqualsAndHashCode(callSuper=false)
public class MemberOrder extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "members_orders_seq")
    private Long idx;

    @Column(name = "mord_state")
    @Builder.Default
    private Integer state = 1;
    private String recName;
    private String recHp;
    private Integer alarm;
    private String zipcode;
    private String address1;
    private String address2;
    private Integer requestType;
    private String requestMemo1;
    private String requestMemo2;
    private Flag greenFlag;
    private Flag dawnFlag;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;
    @ManyToOne(targetEntity = Coupon.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_idx")
    @Nullable
    private Coupon coupon;
    @OneToOne(mappedBy = "order")
    private MemberPayment payment;

}