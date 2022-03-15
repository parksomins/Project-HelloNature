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
        name = "nonmembers_payments_seq",
        sequenceName = "nonmembers_payments_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_nonmembers_payments")
@EqualsAndHashCode(callSuper=false)
public class NonMemberPayment extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nonmembers_payments_seq")
    private Long idx;

    @OneToOne(targetEntity = NonMemberOrder.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nmord_idx")
    private NonMemberOrder order;

    private Integer price;

    @Column(name = "nmpaym_state")
    private Integer state;
    private Integer paymentType;

    @Column(name = "nmpaym_num")
    private String num;
}
