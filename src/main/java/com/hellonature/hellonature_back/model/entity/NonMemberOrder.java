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
        name = "nonmembers_orders_seq",
        sequenceName = "nonmembers_orders_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_nonmembers_orders")
@ToString(exclude = "payment")
@EqualsAndHashCode(callSuper=false)
public class NonMemberOrder extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nonmembers_orders_seq")
    private Long idx;

    @Column(name = "nmord_state")
    @Builder.Default
    private Integer state = 1;

    @Column(name = "nmord_name")
    private String name;
    private String hp;
    private Integer alarm;
    private String zipcode;
    private String address1;
    private String address2;
    private Integer requestType;
    private String requestMemo1;
    private String requestMemo2;

    @OneToOne(mappedBy = "order")
    private NonMemberPayment payment;
}
