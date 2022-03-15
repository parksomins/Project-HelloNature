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
        name = "products_reviews_seq",
        sequenceName = "products_reviews_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_products_reviews")
@EqualsAndHashCode(callSuper=false)
public class ProductReview extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_reviews_seq")
    private Long idx;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_idx")
    private Product product;

    @ManyToOne(targetEntity = MemberOrder.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mord_idx")
    private MemberOrder order;

    @OneToOne(targetEntity = MemberOrderProduct.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mordp_idx")
    private MemberOrderProduct memberOrderProduct;

    @Column(name = "rv_like")
    private Integer like;

    @Column(name = "rv_content")
    private String content;
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private Flag ansFlag = Flag.FALSE;
    private String ansContent;
    private String files;
    private String ansDate;
}