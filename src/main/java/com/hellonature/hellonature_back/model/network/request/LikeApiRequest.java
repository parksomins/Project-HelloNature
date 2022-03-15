package com.hellonature.hellonature_back.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeApiRequest {
    private Long idx;
    private Long memIdx;
    private Long proIdx;
    private Long brIdx;
    private Long mgIdx;
    private Long evIdx;

    private LocalDateTime regdate;

}
