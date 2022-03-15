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
        name = "popupstores_seq",
        sequenceName = "popupstores_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_popupstores")
@EqualsAndHashCode(callSuper=false)
public class Popupstore {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "popupstores_seq")
    private Long idx;
    private String img;
    private String title;
    private String des;

    @ManyToOne(targetEntity = Brand.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "br_idx")
    private Brand brand;

    @Column(name = "pop_content")
    private String content;
    private String dateStart;
    private String dateEnd;
}
