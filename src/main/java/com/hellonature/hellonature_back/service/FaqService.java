package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Faq;
import com.hellonature.hellonature_back.model.entity.Magazine;
import com.hellonature.hellonature_back.model.entity.Question;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.FaqApiRequest;
import com.hellonature.hellonature_back.model.network.response.FaqApiResponse;
import com.hellonature.hellonature_back.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaqService extends BaseService<FaqApiRequest, FaqApiResponse, Faq> {

    private final EntityManager em;
    private final FaqRepository faqRepository;

    @Override
    public Header<FaqApiResponse> create(Header<FaqApiRequest> request) {
        FaqApiRequest faqApiRequest = request.getData();

        Faq faq = Faq.builder()
                .type(faqApiRequest.getType())
                .subject(faqApiRequest.getSubject())
                .title(faqApiRequest.getTitle())
                .content(faqApiRequest.getContent())
                .build();
        Faq newFaq = faqRepository.save(faq);
        return Header.OK();
    }

    @Override
    public Header<FaqApiResponse> read(Long id) {
        return faqRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }

    @Override
    public Header<FaqApiResponse> update(Header<FaqApiRequest> request) {
        FaqApiRequest faqApiRequest = request.getData();
        Optional<Faq> optional = faqRepository.findById(faqApiRequest.getIdx());
        return optional.map(faq -> {
                    faq.setType(faqApiRequest.getType());
                    faq.setSubject(faqApiRequest.getSubject());
                    faq.setTitle(faqApiRequest.getTitle());
                    faq.setContent(faqApiRequest.getContent());
                    return faq;
                }).map(faqRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }

    @Override
    public Header delete(Long id) {
        Optional<Faq> optional = faqRepository.findById(id);
        return optional.map(faq -> {
            faqRepository.delete(faq);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));

    }

    private FaqApiResponse response(Faq faq){
        return FaqApiResponse.builder()
                .idx(faq.getIdx())
                .type(faq.getType())
                .subject(faq.getSubject())
                .title(faq.getTitle())
                .content(faq.getContent())
                .regdate(faq.getRegdate())
                .build();
    }

    public Header<List<FaqApiResponse>> list(Integer type, String subject, String title, String content, Integer startPage) {
        String jpql = "select f from Faq f";
        boolean check = false;

        if ( type != null || title != null || content != null) {
            jpql += " where";
            if (type != null) {
                jpql += " faq_type = :type";
                check = true;
            }
            if (subject != null) {
                if (check) jpql += " and";
                jpql += " subject like :subject";
                check = true;
            }
            if (title != null) {
                if (check) jpql += " and";
                jpql += " title like :title";
                check = true;
            }
            if(content != null) {
                if (check) jpql += " and";
                jpql += " content like :content";
            }
        }

        jpql += " order by idx desc";
        TypedQuery<Faq> query = em.createQuery(jpql, Faq.class);

        if (type != null) query = query.setParameter("type", type);
        if (subject != null) query = query.setParameter("subject", "%"+subject+"%");
        if (title != null) query = query.setParameter("title", "%"+title+"%");
        if (content != null) query = query.setParameter("content", "%"+content+"%");

        List<Faq> result = query.getResultList();

        int count = 10;
        int start = count * startPage; // 현재 보여지는 페이지의 첫번째 element에 해당하는 result의 index
        int end = Math.min(result.size(), start + count); // 그 페이지으 ㅣ마지막 element

        Pagination pagination = new Pagination().builder()
                .totalPages(result.size()/count) // 리스트가 홀수일 때
                .totalElements((long) result.size())
                .currentPage(startPage)
                .currentElements(end - start)
                .build();

        List<FaqApiResponse> newList = new ArrayList<>();

        for(Faq faq : result.subList(start, end)){
            newList.add(response(faq));
        }

        return Header.OK(newList, pagination);
    }


    public Header<List<FaqApiResponse>> search(Pageable pageable){
        Page<Faq> faq = faqRepository.findAll(pageable);
        List<FaqApiResponse> faqApiResponseList = faq.stream()
                .map(this::response)
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(faq.getTotalPages()-1)
                .totalElements(faq.getTotalElements())
                .currentPage(faq.getNumber())
                .currentElements(faq.getNumber())
                .build();
        return Header.OK(faqApiResponseList, pagination);
    }


    @Transactional
    public Header deletePost(List<Long> idx) {
        faqRepository.deleteAllByIdxIn(idx);
        return Header.OK();
    }

    public Header<List<FaqApiResponse>> userList(Integer type){
        List<Faq> faqs = faqRepository.findAllByType(type);
        List<FaqApiResponse> list = new ArrayList<>();
        for (Faq faq: faqs){
            list.add(response(faq));
        }
        return Header.OK(list);
    }
}
