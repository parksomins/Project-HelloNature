package com.hellonature.hellonature_back.model.entity;

import com.hellonature.hellonature_back.model.enumclass.Bank;
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
        name = "cards_seq",
        sequenceName = "cards_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_cards")
@EqualsAndHashCode(callSuper=false)
public class Card extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cards_seq")
    private Long idx;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    @Column(name = "cd_num")
    private String num;
    @Column(name = "cd_date")
    private String date;
    private String cvc;
    @Column(name = "cd_password")
    private String password;
    @Column(name = "cd_name")
    private String name;
    private Bank bank;
    @Builder.Default
    private Flag baseFlag = Flag.FALSE;
    @Builder.Default
    private Flag favFlag = Flag.FALSE;
    @Builder.Default
    private Flag busiFlag = Flag.FALSE;
    private String birth;
}