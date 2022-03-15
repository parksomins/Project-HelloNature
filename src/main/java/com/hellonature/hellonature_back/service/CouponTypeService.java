package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.CouponType;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.CouponTypeApiRequest;
import com.hellonature.hellonature_back.model.network.response.CouponTypeApiResponse;
import com.hellonature.hellonature_back.repository.CouponTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponTypeService extends BaseService<CouponTypeApiRequest, CouponTypeApiResponse, CouponType>{

    private final CouponTypeRepository couponTypeRepository;
    private final EntityManager em;

    @Override
    public Header<CouponTypeApiResponse> create(Header<CouponTypeApiRequest> request) {
        CouponTypeApiRequest couponTypeApiRequest = request.getData();

        CouponType couponType = CouponType.builder()
                .title(couponTypeApiRequest.getTitle())
                .count(couponTypeApiRequest.getCount())
                .discount(couponTypeApiRequest.getDiscount())
                .minPrice(couponTypeApiRequest.getMinPrice())
                .dateStart(couponTypeApiRequest.getDateStart())
                .dateEnd(couponTypeApiRequest.getDateEnd())
                .build();

        couponTypeRepository.save(couponType);

        return Header.OK();
    }

    @Override
    public Header<CouponTypeApiResponse> read(Long id) {
        return couponTypeRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("쿠폰 정보가 없습니다"));
    }

    @Override
    public Header<CouponTypeApiResponse> update(Header<CouponTypeApiRequest> request) {
        CouponTypeApiRequest couponTypeApiRequest = request.getData();
        return couponTypeRepository.findById(couponTypeApiRequest.getIdx())
                .map(couponType -> {
                    couponType.setTitle(couponTypeApiRequest.getTitle());
                    couponType.setCount(couponTypeApiRequest.getCount());
                    couponType.setDiscount(couponType.getDiscount());
                    couponType.setMinPrice(couponType.getMinPrice());
                    couponType.setDateStart(couponType.getDateStart());
                    couponType.setDateEnd(couponType.getDateEnd());

                    return couponType;
                })
                .map(couponTypeRepository::save)
                .map(this::response)
                .map(Header::OK).orElseGet(() -> Header.ERROR("쿠폰 수정이 실패했습니다"));
    }

    @Override
    public Header delete(Long id) {
        Optional<CouponType> optionalCouponType = couponTypeRepository.findById(id);
        return optionalCouponType.map(couponType -> {
            couponTypeRepository.delete(couponType);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("쿠폰 삭제를 실패했습니다"));
    }

    private CouponTypeApiResponse response(CouponType couponType){
        return CouponTypeApiResponse.builder()
                .idx(couponType.getIdx())
                .title(couponType.getTitle())
                .count(couponType.getCount())
                .discount(couponType.getDiscount())
                .minPrice(couponType.getMinPrice())
                .dateStart(couponType.getDateStart())
                .dateEnd(couponType.getDateEnd())
                .regdate(couponType.getRegdate())
                .build();
    }

    public Header<List<CouponTypeApiResponse>> list(String title, String dateStart, String dateEnd, Integer page){
        String jpql = "select ct from CouponType ct";
        boolean check = false;

        if (title != null || dateStart != null || dateEnd != null){
            jpql += " where";
            if (title != null){
                jpql += " title like :title";
                check = true;
            }
            if (dateStart != null){
                if (check) jpql += " and";
                jpql += " TO_char(regdate, 'YYYY-MM-DD') >= :dateStart";
                check = true;
            }
            if(dateEnd != null){
                if (check) jpql += " and";
                jpql += " TO_char(regdate, 'YYYY-MM-DD') <= :dateEnd";
            }
        }

        jpql += " order by idx";
        TypedQuery<CouponType> query = em.createQuery(jpql, CouponType.class);

        if (title != null) query = query.setParameter("title", "%" + title + "%");
        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);

        List<CouponType> couponTypes = query.getResultList();
        List<CouponTypeApiResponse> couponTypeApiResponses = new ArrayList<>();
        int count = 10;
        int start = count * page;
        int end = Math.min(couponTypes.size(), start + count);

        for (CouponType couponType: couponTypes.subList(start, end)){
            couponTypeApiResponses.add(response(couponType));
        }

        Pagination pagination = new Pagination().builder()
                .totalPages(couponTypes.size() / count)
                .totalElements((long) couponTypes.size())
                .currentPage(page)
                .currentElements(end - start)
                .build();

        return Header.OK(couponTypeApiResponses, pagination);
    }
    @Transactional
    public Header deletePost(List<Long> idx) {
        couponTypeRepository.deleteAllByIdxIn(idx);
        return Header.OK();
    }
}
