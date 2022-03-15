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
        name = "brands_seq",
        sequenceName = "brands_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_brands")
@EqualsAndHashCode(callSuper=false)
public class Brand extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brands_seq")
    private Long idx;

    @Column(name = "br_name")
    private String name;
    private String des;
    private String logo;
    @Column(name = "br_state")
    private Integer state;
    private String banner;
    private String dateStart;
    private String dateEnd;
}
