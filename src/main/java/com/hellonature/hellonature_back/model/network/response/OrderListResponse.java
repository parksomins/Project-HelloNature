package com.hellonature.hellonature_back.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderListResponse {
    private Long idx;
    private String name;
    private String proList;
    private Long proIdx;
    private Integer price;
    private LocalDateTime regdate;
    private Integer state;
}