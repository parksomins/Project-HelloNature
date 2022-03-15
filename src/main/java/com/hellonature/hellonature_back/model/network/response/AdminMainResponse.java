package com.hellonature.hellonature_back.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminMainResponse {
    private AdminPreviewResponse preview;
    private AdminDailyResponse daily;
    private AdminUsersResponse users;
}
