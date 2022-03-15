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
        name = "members_likes_seq",
        sequenceName = "members_likes_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_members_likes")
@EqualsAndHashCode(callSuper=false)
public class Like extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "members_likes_seq")
    private Long idx;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_idx")
    @Nullable
    private Product product;

    @ManyToOne(targetEntity = Magazine.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mg_idx")
    @Nullable
    private Magazine magazine;
}
