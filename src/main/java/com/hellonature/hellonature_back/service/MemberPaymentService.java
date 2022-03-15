package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.MemberPayment;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.MemberPaymentApiRequest;
import com.hellonature.hellonature_back.model.network.response.MemberPaymentApiResponse;
import com.hellonature.hellonature_back.repository.MemberOrderRepository;
import com.hellonature.hellonature_back.repository.MemberPaymentRepository;
import com.hellonature.hellonature_back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberPaymentService extends BaseService<MemberPaymentApiRequest, MemberPaymentApiResponse, MemberPayment> {

    private final EntityManager em;
    private final MemberRepository memberrepository;
    private final MemberOrderRepository memberOrderRepository;
    private final MemberPaymentRepository memberPaymentRepository;

    @Override
    public Header<MemberPaymentApiResponse> create(Header<MemberPaymentApiRequest> request) {
        MemberPaymentApiRequest memberPaymentApiRequest = request.getData();

        MemberPayment memberPayment = MemberPayment.builder()
                .order(memberOrderRepository.findById(memberPaymentApiRequest.getIdx()).get())
                .member(memberrepository.findById(memberPaymentApiRequest.getIdx()).get())
                .price(memberPaymentApiRequest.getPrice())
                .state(memberPaymentApiRequest.getState())
                .paymentType(memberPaymentApiRequest.getPaymentType())
                .num(memberPaymentApiRequest.getNum())
                .build();
        MemberPayment newMemberPayment = memberPaymentRepository.save(memberPayment);
        return Header.OK();
    }

    @Override
    public Header<MemberPaymentApiResponse> read(Long idx) {
        return memberPaymentRepository.findById(idx)
                .map(memberPayment -> response(memberPayment))
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }

    @Override
    public Header<MemberPaymentApiResponse> update(Header<MemberPaymentApiRequest> request) {
        MemberPaymentApiRequest memberPaymentApiRequest = request.getData();
        Optional<MemberPayment> optional = memberPaymentRepository.findById(memberPaymentApiRequest.getIdx());
        return optional.map(memberPayment -> {
            memberPayment.setPrice(memberPaymentApiRequest.getPrice());
            memberPayment.setPaymentType(memberPaymentApiRequest.getPaymentType());
            memberPayment.setState(memberPaymentApiRequest.getState());
            memberPayment.setNum(memberPaymentApiRequest.getNum());
            return memberPayment;
        }).map(memberPayment -> memberPaymentRepository.save(memberPayment))
                .map(memberPayment -> response(memberPayment))
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }

    @Override
    public Header delete(Long idx) {
        Optional<MemberPayment> optional = memberPaymentRepository.findById(idx);

        return optional.map(memberPayment -> {
            memberPaymentRepository.delete(memberPayment);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("No data"));
    }

    private MemberPaymentApiResponse response(MemberPayment memberPayment){
        MemberPaymentApiResponse memberPaymentApiResponse = MemberPaymentApiResponse.builder()
                .idx(memberPayment.getIdx())
//                .mordIdx(memberPayment.getOrder().getIdx())
                .memName(memberPayment.getMember().getName())
                .state(memberPayment.getState())
                .price(memberPayment.getPrice())
                .paymentType(memberPayment.getPaymentType())
                .regdate(memberPayment.getRegdate())
                .build();
        return memberPaymentApiResponse;
    }

    public Header<List<MemberPaymentApiResponse>> list(String dateStart, String dateEnd, Long mordIdx, Integer startPage){

        String jpql = "select mp from MemberPayment mp";
        boolean check = false;
        //ArrayList<Question> arrayList = new ArrayList<>();

        if(dateStart != null || dateEnd != null || mordIdx != null ){
            jpql += " where";
            if (dateStart != null){
                jpql += " TO_char(regdate, 'YYYY-MM-DD') >= :dateStart";
                check = true;
            }
            if(dateEnd != null){
                if (check) jpql += " and";
                jpql += " TO_char(regdate, 'YYYY-MM-DD') <= :dateEnd";
            }
            if(mordIdx != null){
                if (check) jpql += " and";
                jpql += " mord_idx = :mordIdx";
                check = true;
            }

        }

        jpql += " order by mp.idx desc";
        System.out.println(jpql);
        TypedQuery<MemberPayment> query = em.createQuery(jpql, MemberPayment.class);



        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);
        if (mordIdx != null) query = query.setParameter("mordIdx", mordIdx);

        List<MemberPayment> result = query.getResultList();

        int count = 10;
        int size = result.size();
        int start = count * startPage;
        int end = Math.min(result.size(), start + count);

        Pagination pagination = new Pagination().builder()
                .totalPages(size % count == 0 ? size/count - 1 : size/count)
                .totalElements((long) result.size())
                .currentPage(startPage+1)
                .currentElements(result.size())
                .build();

        return Header.OK(result.subList(start, end).stream().map(this::response).collect(Collectors.toList()), pagination);
    }

    @Transactional
    public Header deletePost(List<Long> idx) {
        memberPaymentRepository.deleteAllByIdxIn(idx);
        return Header.OK();
    }
}
