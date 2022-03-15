package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Notice;
import com.hellonature.hellonature_back.model.entity.ProductQuestion;
import com.hellonature.hellonature_back.model.entity.ProductReview;
import com.hellonature.hellonature_back.model.entity.Question;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.response.*;
import com.hellonature.hellonature_back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final MagazineRepository magazineRepository;
    private final MemberOrderRepository memberOrderRepository;
    private final ProductQuestionRepository productQuestionRepository;
    private final ProductReviewRepository productReviewRepository;
    private final QuestionRepository questionRepository;
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    public Header<AdminMainResponse> mainPage(){
        AdminDailyResponse adminDailyResponse = adminDailyResponse();
        AdminPreviewResponse adminPreviewResponse = adminPreviewResponse();
        AdminUsersResponse adminUsersResponse = adminUsersResponse();

        return Header.OK(AdminMainResponse.builder()
                        .daily(adminDailyResponse)
                        .preview(adminPreviewResponse)
                        .users(adminUsersResponse)
                .build());
    }

    private AdminDailyResponse adminDailyResponse(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        LocalDateTime end = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
        calendar.add(Calendar.DATE, -1);
        LocalDateTime start = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());

        return AdminDailyResponse.builder()
                .product1(productRepository.countAllByState(1))
                .product2(productRepository.countAllByState(2))
                .product3(productRepository.countAllByCount(0))
                .brand1(brandRepository.countAllByState(2))
                .brand2(brandRepository.countAllByState(3))
                .magazine1(magazineRepository.countAllByShowFlag(Flag.TRUE))
                .magazine2(magazineRepository.countAllByShowFlag(Flag.FALSE))
                .order1(memberOrderRepository.countAllByRegdateBetween(start, end))
                .order2(memberOrderRepository.countAllByStateAndRegdateBetween(1, start, end))
                .order3(memberOrderRepository.countAllByStateAndRegdateBetween(5, start, end))
                .order4(memberOrderRepository.countAllByRegdateBetweenAndStateIsGreaterThanEqual(start, end, 6))
                .productQuestion1(productQuestionRepository.countAllByAnsFlag(Flag.FALSE))
                .productQuestion2(productReviewRepository.countAllByAnsFlag(Flag.FALSE))
                .question1(questionRepository.countAllByAnsFlag(Flag.FALSE))
                .build();
    }

    private AdminPreviewResponse adminPreviewResponse(){
        return AdminPreviewResponse.builder()
                .notices(noticeRepository.findTop4ByOrderByIdxDesc().stream().map(this::noticeApiResponse).collect(Collectors.toList()))
                .questions(questionRepository.findTop4ByOrderByIdxDesc().stream().map(this::questionApiResponse).collect(Collectors.toList()))
                .productReviews(productReviewRepository.findTop4ByContentIsNotNullOrderByIdxDesc().stream().map(this::productReviewApiResponse).collect(Collectors.toList()))
                .productQuestions(productQuestionRepository.findTop4ByOrderByIdxDesc().stream().map(this::productQuestionApiResponse).collect(Collectors.toList()))
                .build();
    }

    private AdminUsersResponse adminUsersResponse(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        List<Long> claimCount = new ArrayList<>();
        List<Long> memberCount = new ArrayList<>();
        List<Long> orderCount = new ArrayList<>();

        for (int i = 0; i < 7 ; i++){
            LocalDateTime end = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
            calendar.add(Calendar.DATE, -1);
            LocalDateTime start = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
            memberCount.add(memberRepository.countAllByRegdateBetween(start, end));
            claimCount.add(memberOrderRepository.countAllByRegdateBetweenAndStateIsGreaterThanEqual(start, end, 6));
            orderCount.add(memberOrderRepository.countAllByRegdateBetweenAndStateIsLessThanEqual(start, end, 5));
        }
        return AdminUsersResponse.builder()
                .claimCount(claimCount)
                .memberCount(memberCount)
                .orderCount(orderCount)
                .build();
    }

    private NoticeApiResponse noticeApiResponse(Notice notice){
        return NoticeApiResponse.builder()
                .idx(notice.getIdx())
                .title(notice.getTitle())
                .build();
    }

    private QuestionApiResponse questionApiResponse(Question question){
        return QuestionApiResponse.builder()
                .idx(question.getIdx())
                .content(question.getContent())
                .build();
    }

    private ProductReviewApiResponse productReviewApiResponse(ProductReview productReview){
        return ProductReviewApiResponse.builder()
                .idx(productReview.getIdx())
                .content(productReview.getContent())
                .build();
    }

    private ProductQuestionApiResponse productQuestionApiResponse(ProductQuestion productQuestion){
        return ProductQuestionApiResponse.builder()
                .idx(productQuestion.getIdx())
                .content(productQuestion.getContent())
                .build();
    }
}
