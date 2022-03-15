package com.hellonature.hellonature_back.model.entity;

import com.hellonature.hellonature_back.model.enumclass.Flag;
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
        name = "products_seq",
        sequenceName = "products_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_products")
@EqualsAndHashCode(callSuper=false)
public class Product extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_seq")
    private Long idx;

    @Column(name = "title")
    private String name;
    private String des;
    private Integer netPrice;
    @Nullable
    @Builder.Default
    private Integer salePrice = 0;
    private Integer price;

    @Column(name = "pro_state")
    private Integer state;
    private String dateStart;
    private String dateEnd;
    private String origin;
    private String sizeWeight;
    private Integer temp;

    @Column(name = "pro_count")
    private Integer count;
    private Integer delivery;
    private Integer packing;
    private String img1;
    @Nullable
    private String img2;
    @Nullable
    private String img3;
    @Nullable
    private String img4;
    private String proDes;

    @Builder.Default
    @Column(name = "pro_like")
    private Integer like = 0;
    @Builder.Default
    private Flag bestFlag = Flag.FALSE;

    private String proType;
    private String proName;
    private String foodType;
    private String producer;

    @Column(name = "pro_location")
    private String location;
    private String dateBuilt;
    private String dateValid;

    @ManyToOne(targetEntity = Brand.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "br_idx")
    private Brand brand;
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_idx")
    private Category category;
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "eve_cate_idx")
    @Nullable
    private Category eveCategory;

    public int plusLike(){
        like += 1;
        return like;
    }

    public int minusLike(){
        like -= 1;
        return like;
    }
}