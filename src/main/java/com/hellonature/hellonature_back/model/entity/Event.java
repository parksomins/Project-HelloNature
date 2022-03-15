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
        name = "events_seq",
        sequenceName = "events_seq",
        initialValue = 1,
        allocationSize = 1
)
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name="tb_events")
@EqualsAndHashCode(callSuper=false)
public class Event extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_seq")
    private Long idx;

    private Flag typeFlag;
    private Flag ingFlag;
    private String dateStart;
    private String dateEnd;
    private String img;
    private String title;
    private String des;

    @Column(name = "eve_content")
    private String content;
}