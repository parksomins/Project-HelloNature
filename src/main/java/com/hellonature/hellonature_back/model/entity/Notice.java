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
        name = "notices_seq",
        sequenceName = "notices_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_notices")
@EqualsAndHashCode(callSuper=false)
public class Notice extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notices_seq")
    private Long idx;

    @Column(name = "n_type")
    private Integer type;
    private String title;

    @Column(name = "n_content")
    private String content;
}
