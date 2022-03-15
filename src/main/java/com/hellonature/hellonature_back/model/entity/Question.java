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
        name = "questions_seq",
        sequenceName = "questions_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_questions")
@EqualsAndHashCode(callSuper=false)
public class Question extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_seq")
    private Long idx;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private Flag ansFlag = Flag.FALSE;
    private String ansDate;
    private String hp;

    @Column(name = "que_content")
    private String content;
    private String ansContent;
    private String files;
    private String email;

    @Column(name = "que_type")
    private Integer type;
}