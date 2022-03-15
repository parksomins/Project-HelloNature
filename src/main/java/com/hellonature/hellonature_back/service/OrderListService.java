package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.*;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.response.MyPageOrderResponse;
import com.hellonature.hellonature_back.model.network.response.OrderListResponse;
import com.hellonature.hellonature_back.repository.MemberOrderProductRepository;
import com.hellonature.hellonature_back.repository.NonMemberOrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderListService {

    private final EntityManager em;

    private final MemberOrderProductRepository memberOrderProductRepository;
    private final NonMemberOrderProductRepository nonMemberOrderProductRepository;

    public Header<List<OrderListResponse>> memberOrderList(String dateStart, String dateEnd, Integer state, Long orderIdx, Integer startPage){
        String jpql = "select o from MemberOrder o";
        boolean check = false;
        if(dateStart != null || dateEnd != null || state != null || orderIdx != null){
            jpql += " where";
            if (dateStart != null){
                jpql += " TO_char(regdate, 'YYYY-MM-DD') >= :dateStart";
                check = true;
            }
            if(dateEnd != null){
                if (check) jpql += " and";
                jpql += " TO_char(regdate, 'YYYY-MM-DD') <= :dateEnd";
            }
            if(state != null){
                if(check) jpql += " and";
                jpql += " mord_state = :state";
            }
            if(orderIdx != null){
                if(check) jpql += " and";
                jpql += " idx = :orderIdx";
            }
        }

        jpql += " order by idx desc";

        TypedQuery<MemberOrder> query = em.createQuery(jpql, MemberOrder.class);
        if (state != null) query = query.setParameter("state", state);
        if (orderIdx != null) query = query.setParameter("orderIdx", orderIdx);
        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);

        List<MemberOrder> result = query.getResultList();
        int count = 10;
        Integer start = count * startPage;
        Integer end = Math.min(result.size(), start + count);

        Pagination pagination = new Pagination().builder()
                .totalPages(result.size() / count)
                .currentPage(startPage)
                .currentElements(end - start)
                .build();

        List<OrderListResponse> orderListResponses = new ArrayList<>();

        for (MemberOrder memberOrder:
                result.subList(start, end)) {
            orderListResponses.add(response(memberOrder));
        }

        return Header.OK(orderListResponses, pagination);
    }

    public Header<List<OrderListResponse>> nonMemberOrderList(String dateStart, String dateEnd, Integer state, Long orderIdx, Integer startPage){
        String jpql = "select o from NonMemberOrder o";
        boolean check = false;
        if(dateStart != null || dateEnd != null || state != null || orderIdx != null){
            jpql += " where";
            if (dateStart != null){
                jpql += " TO_char(regdate, 'YYYY-MM-DD') >= :dateStart";
                check = true;
            }
            if(dateEnd != null){
                if (check) jpql += " and";
                jpql += " TO_char(regdate, 'YYYY-MM-DD') <= :dateEnd";
            }
            if(state != null){
                if(check) jpql += " and";
                jpql += " nmord_state = :state";
            }
            if(orderIdx != null){
                if(check) jpql += " and";
                jpql += " idx = :orderIdx";
            }
        }

        jpql += " order by idx desc";

        TypedQuery<NonMemberOrder> query = em.createQuery(jpql, NonMemberOrder.class);
        if (state != null) query = query.setParameter("state", state);
        if (orderIdx != null) query = query.setParameter("orderIdx", orderIdx);
        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);

        List<NonMemberOrder> result = query.getResultList();
        int count = 10;
        Integer start = count * startPage;
        Integer end = Math.min(result.size(), start + count);

        Pagination pagination = new Pagination().builder()
                .totalPages(result.size() / count)
                .totalElements((long) result.size())
                .currentPage(startPage)
                .currentElements(end - start)
                .build();

        List<OrderListResponse> orderListResponses = new ArrayList<>();

        for (NonMemberOrder nonMemberOrder:
                result.subList(start, end)) {
            orderListResponses.add(response(nonMemberOrder));
        }

        return Header.OK(orderListResponses, pagination);
    }

    public OrderListResponse response(MemberOrder memberOrder){
        List<MemberOrderProduct> memberOrderProducts = memberOrderProductRepository.findAllByOrder(memberOrder);
        String temp = "";

        for (MemberOrderProduct memberOrderProduct:
                memberOrderProducts) {
            temp += memberOrderProduct.getProduct().getName() + " " + memberOrderProduct.getProCount();
        }

        return OrderListResponse.builder()
                .idx(memberOrder.getIdx())
                .name(memberOrder.getMember().getName())
                .price(memberOrder.getPayment().getPrice())
                .regdate(memberOrder.getRegdate())
                .state(memberOrder.getState())
                .proList(temp)
                .proIdx(memberOrder.getPayment().getIdx())
                .build();
    }

    public OrderListResponse response(NonMemberOrder nonMemberOrder){
        List<NonMemberOrderProduct> nonMemberOrderProducts = nonMemberOrderProductRepository.findAllByOrder(nonMemberOrder);
        String temp = "";

        for (NonMemberOrderProduct nonMemberOrderProduct:
                nonMemberOrderProducts) {
            temp += nonMemberOrderProduct.getProduct().getName() + " " + nonMemberOrderProduct.getProCount();
        }

        return OrderListResponse.builder()
                .idx(nonMemberOrder.getIdx())
                .name(nonMemberOrder.getName())
                .price(nonMemberOrder.getPayment().getPrice())
                .regdate(nonMemberOrder.getRegdate())
                .state(nonMemberOrder.getState())
                .proList(temp)
                .proIdx(nonMemberOrder.getPayment().getIdx())
                .build();
    }

    public Header<List<MyPageOrderResponse>> myPage(String dateStart, String dateEnd, Integer type, Long memIdx){
        String jpql = "select mo from MemberOrder mo where";
        jpql += " mem_idx = :memIdx";
        jpql += " and TO_char(regdate, 'YYYY-MM-DD') >= :dateStart";
        jpql += " and TO_char(regdate, 'YYYY-MM-DD') <= :dateEnd";
        if (type == 1) jpql += " and mo.state <= 5";
        else jpql += " and mo.state >= 6";

        jpql += " order by idx desc";

        TypedQuery<MemberOrder> query = em.createQuery(jpql, MemberOrder.class);
        query.setParameter("dateStart", dateStart);
        query.setParameter("dateEnd", dateEnd);
        query.setParameter("memIdx", memIdx);

        List<MemberOrder> result = query.getResultList();

        List<MemberOrderProduct> memberOrderProducts = memberOrderProductRepository.findAllByOrderIn(result, Sort.by(Sort.Direction.DESC,"idx"));

        List<MyPageOrderResponse> list = new ArrayList<>();

        for (MemberOrderProduct memberOrderProduct: memberOrderProducts) {
            list.add(myPageOrderListResponse(memberOrderProduct));
        }

        return Header.OK(list);
    }

    private MyPageOrderResponse myPageOrderListResponse(MemberOrderProduct memberOrderProduct){
        Product product = memberOrderProduct.getProduct();
        MemberOrder memberOrder = memberOrderProduct.getOrder();
        return MyPageOrderResponse.builder()
                .proIdx(product.getIdx())
                .proName(product.getName())
                .proImg(product.getImg1())
                .proWeightSize(product.getSizeWeight())
                .proCount(memberOrderProduct.getProCount())
                .state(memberOrder.getState())
                .regdate(memberOrderProduct.getRegdate())
                .dawnFlag(memberOrder.getDawnFlag())
                .greenFlag(memberOrder.getGreenFlag())
                .rvIdx(memberOrderProduct.getProductReview() == null ? null : memberOrderProduct.getProductReview().getIdx())
                .recName(memberOrder.getRecName())
                .recHp(memberOrder.getRecHp())
                .zipcode(memberOrder.getZipcode())
                .address1(memberOrder.getAddress1())
                .address2(memberOrder.getAddress2())
                .build();
    }
}