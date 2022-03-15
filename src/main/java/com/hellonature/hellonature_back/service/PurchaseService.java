package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Product;
import com.hellonature.hellonature_back.model.entity.Purchase;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.PurchaseApiRequest;
import com.hellonature.hellonature_back.model.network.response.PurchaseApiResponse;
import com.hellonature.hellonature_back.repository.MemberRepository;
import com.hellonature.hellonature_back.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService{

    private final PurchaseRepository purchaseRepository;
    private final MemberRepository memberRepository;

    public Header<List<PurchaseApiResponse>> list(Long memIdx, String dateStart, String dateEnd){
        String[] temp;
        temp = dateStart.split("-");
        LocalDateTime start = LocalDateTime.of(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), 00, 00);
        temp = dateEnd.split("-");
        LocalDateTime end = LocalDateTime.of(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), 23, 59);

        List<Purchase> result = purchaseRepository.findAllByMemberAndRegdateBetweenOrderByCountDesc(memberRepository.findById(memIdx).get(), start, end);
        List<PurchaseApiResponse> list = new ArrayList<>();

        for (Purchase purchase: result) {
            list.add(response(purchase));
        }
        return Header.OK(list);
    }

    private PurchaseApiResponse response(Purchase purchase){
        Product product = purchase.getProduct();
        return PurchaseApiResponse.builder()
                .idx(purchase.getIdx())
                .count(purchase.getCount())
                .memIdx(purchase.getMember().getIdx())
                .proIdx(product.getIdx())
                .proName(product.getName())
                .proImg(product.getImg1())
                .proWeightSize(product.getSizeWeight())
                .regdate(purchase.getRegdate())
                .build();
    }
}
