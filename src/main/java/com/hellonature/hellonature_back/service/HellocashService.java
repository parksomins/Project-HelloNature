package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Hellocash;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.HellocashApiRequest;
import com.hellonature.hellonature_back.model.network.response.HelloCashApiResponse;
import com.hellonature.hellonature_back.repository.HellocashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HellocashService extends BaseService<HellocashApiRequest, HelloCashApiResponse, Hellocash>{

    private final HellocashRepository hellocashRepository;
    private final EntityManager em;

    @Override
    public Header<HelloCashApiResponse> create(Header<HellocashApiRequest> request) {
        return null;
    }

    @Override
    public Header<HelloCashApiResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<HelloCashApiResponse> update(Header<HellocashApiRequest> request) {
        return null;
    }

    @Override
    public Header<HelloCashApiResponse> delete(Long id) {
        return null;
    }

    public Header<List<HelloCashApiResponse>> list(String dateStart, String dateEnd, Long memIdx){
        String jpql = "select h from Hellocash h where mem_idx = :memIdx and TO_char(regdate, 'YYYY-MM-DD') >= :dateStart and TO_char(regdate, 'YYYY-MM-DD') <= :dateEnd";

        jpql += " order by idx desc";
        TypedQuery<Hellocash> query = em.createQuery(jpql, Hellocash.class)
                .setParameter("memIdx", memIdx)
                .setParameter("dateStart", dateStart)
                .setParameter("dateEnd", dateEnd);

        List<Hellocash> result = query.getResultList();
        List<HelloCashApiResponse> newList = new ArrayList<>();

        for (Hellocash hellocash: result) {
            newList.add(response(hellocash));
        }

        return Header.OK(newList);
    }

    private HelloCashApiResponse response(Hellocash hellocash){
        HelloCashApiResponse helloCashApiResponse = HelloCashApiResponse.builder()
                .idx(hellocash.getIdx())
                .memIdx(hellocash.getMember().getIdx())
                .point(hellocash.getPoint())
                .dateUsed(hellocash.getDateUsed())
                .dateVal(hellocash.getDateVal())
                .type(hellocash.getType())
                .title(hellocash.getTitle())
                .build();
        return helloCashApiResponse;
    }
}
