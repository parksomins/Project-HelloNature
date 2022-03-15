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
        name = "faqs_seq",
        sequenceName = "faqs_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_faqs")
@EqualsAndHashCode(callSuper=false)
public class Faq extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faqs_seq")
    private Long idx;

    @Column(name = "faq_type")
    private Integer type;

    private String subject;
    private String title;


    @Column(name = "faq_content")
    private String content;
}
