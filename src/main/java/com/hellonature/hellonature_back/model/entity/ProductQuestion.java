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
        name = "products_questions_seq",
        sequenceName = "products_questions_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_products_questions")
@EqualsAndHashCode(callSuper=false)
public class ProductQuestion extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_questions_seq")
    private Long idx;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_idx")
    private Product product;

    @Column(name = "proq_content")
    private String content;
    private String img;

    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private Flag ansFlag = Flag.FALSE;
    private String ansContent;
    private String ansDate;
}