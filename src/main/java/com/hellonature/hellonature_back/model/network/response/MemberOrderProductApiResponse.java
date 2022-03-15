package com.hellonature.hellonature_back.model.network.response;

import com.hellonature.hellonature_back.model.entity.MemberOrder;
import com.hellonature.hellonature_back.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberOrderProductApiResponse {

    private Long idx;
    private Long order;

    private Long proIdx;

    private Integer proCount;
    private Integer proPrice;

    private LocalDateTime regdate;
}
