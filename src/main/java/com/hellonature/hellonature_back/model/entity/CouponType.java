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
        name = "coupons_types_seq",
        sequenceName = "coupons_types_seq",
        initialValue = 31546878,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_coupons_types")
@EqualsAndHashCode(callSuper=false)
public class CouponType extends DateEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coupons_types_seq")
    private Long idx;

    private String title;
    @Column(name = "ct_target")
    @Builder.Default
    private Integer target = 1;
    @Column(name = "ct_auto")
    @Builder.Default
    private Flag auto = Flag.FALSE;

    @Column(name = "ct_count")
    private Integer count;
    private Integer discount;
    @Builder.Default
    private Integer minPrice = 0;
    private String dateStart;
    @Builder.Default
    private String dateEnd = "9999-12-31";
    @Builder.Default
    private Integer type1 = 1;
    private String type2;

    public int plusCount(){
        count += 1;
        return count;
    }

    public int minusCount(){
        count -= 1;
        return count;
    }
}