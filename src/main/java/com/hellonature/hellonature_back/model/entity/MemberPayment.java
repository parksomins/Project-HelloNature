package com.hellonature.hellonature_back.model.entity;

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
        name = "members_payments_seq",
        sequenceName = "members_payments_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_members_payments")
@EqualsAndHashCode(callSuper=false)
public class MemberPayment extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "members_payments_seq")
    private Long idx;

    @OneToOne(targetEntity = MemberOrder.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mord_idx")
    @Nullable
    private MemberOrder order;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    private Integer price;

    @Column(name = "mpaym_state")
    private Integer state;
    private Integer paymentType;

    @Column(name = "mpaym_num")
    private String num;
}
