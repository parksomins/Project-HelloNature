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
        name = "members_orders_products_seq",
        sequenceName = "members_orders_products_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_members_orders_products")
@EqualsAndHashCode(callSuper=false)
public class MemberOrderProduct extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "members_orders_seq")
    private Long idx;

    @ManyToOne(targetEntity = MemberOrder.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ord_idx")
    private MemberOrder order;
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_idx")
    private Product product;

    @OneToOne(mappedBy = "memberOrderProduct")
    @Nullable
    private ProductReview productReview;

    private Integer proCount;
    private Integer proPrice;
}
