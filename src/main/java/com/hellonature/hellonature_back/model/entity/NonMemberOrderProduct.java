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
        name = "nonmembers_orders_products_seq",
        sequenceName = "nonmembers_orders_products_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_nonmembers_orders_products")
@EqualsAndHashCode(callSuper=false)
public class NonMemberOrderProduct extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nonmembers_orders_products_seq")
    private Long idx;

    @ManyToOne(targetEntity = NonMemberOrder.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nmem_idx")
    private NonMemberOrder order;

    @ManyToOne(targetEntity = MemberOrder.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_idx")
    private Product product;

    private Integer proCount;
    private Integer proPrice;
}