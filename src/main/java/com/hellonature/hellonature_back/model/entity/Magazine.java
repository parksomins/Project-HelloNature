package com.hellonature.hellonature_back.model.entity;

import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.enumclass.MagazineType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "magazines_seq",
        sequenceName = "magazines_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_magazines")
@EqualsAndHashCode(callSuper=false)
public class Magazine extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "magazines_seq")
    private Long idx;

    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private Flag showFlag = Flag.TRUE;
    private String img;
    private String title;
    private String des;
    @Column(name = "mg_content")
    private String content;

    @Column(name = "mg_like")
    @Builder.Default
    private Integer like = 0;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "mg_type")
    private MagazineType type;

    private String cookTime;
    private Integer cookKcal;
    private Integer cookLevel;
    private String cookIngredient;

    private String ingreList;
    private String relList;

    public int plusLike(){
        like += 1;
        return like;
    }

    public int minusLike(){
        like -= 1;
        return like;
    }
}