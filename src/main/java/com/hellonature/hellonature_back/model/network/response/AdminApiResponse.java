package com.hellonature.hellonature_back.model.network.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminApiResponse {
    private Long idx;

    private String name;
    private String id;
    private String password;
    private LocalDateTime regdate;

}
