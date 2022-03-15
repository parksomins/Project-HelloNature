package com.hellonature.hellonature_back.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminPreviewResponse {

    private List<NoticeApiResponse> notices;
    private List<QuestionApiResponse> questions;
    private List<ProductReviewApiResponse> productReviews;
    private List<ProductQuestionApiResponse> productQuestions;
}
