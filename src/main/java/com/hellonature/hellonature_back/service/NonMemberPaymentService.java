package com.hellonature.hellonature_back.service;


import com.hellonature.hellonature_back.model.entity.NonMemberPayment;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.NonMemberPaymentApiRequest;
import com.hellonature.hellonature_back.model.network.response.NonMemberPaymentApiResponse;
import com.hellonature.hellonature_back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NonMemberPaymentService extends BaseService<NonMemberPaymentApiRequest, NonMemberPaymentApiResponse, NonMemberPayment> {
    @Autowired
    EntityManager em;


    @Autowired(required = false)
    private NonMemberOrderRepository nonMemberOrderRepository;

    @Autowired(required = false)
    private NonMemberPaymentRepository nonMemberPaymentRepository;


    @Override
    public Header<NonMemberPaymentApiResponse> create(Header<NonMemberPaymentApiRequest> request) {
        NonMemberPaymentApiRequest nonMemberPaymentApiRequest = request.getData();

        NonMemberPayment nonMemberPayment = NonMemberPayment.builder()
                .order(nonMemberOrderRepository.findById(nonMemberPaymentApiRequest.getIdx()).get())
                .price(nonMemberPaymentApiRequest.getPrice())
                .state(nonMemberPaymentApiRequest.getState())
                .paymentType(nonMemberPaymentApiRequest.getPaymentType())
                .num(nonMemberPaymentApiRequest.getNum())
                .build();
        NonMemberPayment newNonMemberPayment =  nonMemberPaymentRepository.save(nonMemberPayment);
        return Header.OK();
    }

    @Override
    public Header<NonMemberPaymentApiResponse> read(Long idx) {
        return nonMemberPaymentRepository.findById(idx)
                .map(nonMemberPayment -> response(nonMemberPayment))
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }

    @Override
    public Header<NonMemberPaymentApiResponse> update(Header<NonMemberPaymentApiRequest> request) {
        NonMemberPaymentApiRequest nonMemberPaymentApiRequest = request.getData();
        Optional<NonMemberPayment> optional = nonMemberPaymentRepository.findById(nonMemberPaymentApiRequest.getIdx());
        return optional.map(nonMemberPayment -> {

                    nonMemberPayment.setPaymentType(nonMemberPaymentApiRequest.getPaymentType());
                    nonMemberPayment.setState(nonMemberPaymentApiRequest.getState());
                    nonMemberPayment.setPrice(nonMemberPaymentApiRequest.getPrice());
                    nonMemberPayment.setNum(nonMemberPaymentApiRequest.getNum());
                    return nonMemberPayment;
                }).map(nonMemberPayment -> nonMemberPaymentRepository.save(nonMemberPayment))
                .map(nonMemberPayment -> response(nonMemberPayment))
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }

    @Override
    public Header delete(Long idx) {
        Optional<NonMemberPayment> optional = nonMemberPaymentRepository.findById(idx);

        return optional.map(nonMemberPayment -> {
            nonMemberPaymentRepository.delete(nonMemberPayment);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("No data"));
    }


    private NonMemberPaymentApiResponse response(NonMemberPayment nonMemberPayment){
        NonMemberPaymentApiResponse nonMemberPaymentApiResponse = NonMemberPaymentApiResponse.builder()
                .idx(nonMemberPayment.getIdx())
                .nmordIdx(nonMemberPayment.getOrder().getIdx())
                .nmordName(nonMemberPayment.getOrder().getName())
                .state(nonMemberPayment.getState())
                .price(nonMemberPayment.getPrice())
                .regdate(nonMemberPayment.getRegdate())
                .paymentType(nonMemberPayment.getPaymentType())
                .build();
        return nonMemberPaymentApiResponse;
    }


    public Header<List<NonMemberPaymentApiResponse>> list(String dateStart, String dateEnd, Long nmordIdx, Integer startPage){

        String jpql = "select nmp from NonMemberPayment nmp";
        boolean check = false;
        //ArrayList<Question> arrayList = new ArrayList<>();

        if(dateStart != null || dateEnd != null || nmordIdx != null ){
            jpql += " where";
            if (dateStart != null){
                jpql += " TO_char(regdate, 'YYYY-MM-DD') >= :dateStart";
                check = true;
            }
            if(dateEnd != null){
                if (check) jpql += " and";
                jpql += " TO_char(regdate, 'YYYY-MM-DD') <= :dateEnd";
            }
            if(nmordIdx != null){
                if (check) jpql += " and";
                jpql += " nmord_idx = :nmordIdx";
                check = true;
            }

        }

        jpql += " order by nmp.idx desc";
        TypedQuery<NonMemberPayment> query = em.createQuery(jpql, NonMemberPayment.class);

        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);
        if (nmordIdx != null) query = query.setParameter("nmordIdx", nmordIdx);

        List<NonMemberPayment> result = query.getResultList();

        int count = 10;

        Integer start = count * startPage;
        Integer end = Math.min(result.size(), start + count);

        Pagination pagination = new Pagination().builder()
                .totalPages( result.size()>=10 ? (result.size()/count)+1 : 1  )
                .totalElements(result.stream().count())
                .currentPage(startPage+1)
                .currentElements(result.size())
                .build();

        return Header.OK(result.subList(start, end).stream().map(nonMemberPayment -> response(nonMemberPayment)).collect(Collectors.toList()), pagination);
    }

    @Transactional
    public Header deletePost(List<Long> idx) {
        nonMemberPaymentRepository.deleteAllByIdxIn(idx);
        return Header.OK();
    }
}
