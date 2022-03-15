package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.*;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.MemberOrderApiRequest;
import com.hellonature.hellonature_back.model.network.request.NonMemberOrderApiRequest;
import com.hellonature.hellonature_back.model.network.response.MemberOrderApiResponse;
import com.hellonature.hellonature_back.model.network.response.NonMemberOrderApiResponse;
import com.hellonature.hellonature_back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOrderService {

    private MemberOrderRepository memberOrderRepository;
    private MemberRepository memberRepository;
    private MemberPaymentRepository memberPaymentRepository;
    private ProductRepository productRepository;
    private MemberOrderProductRepository memberOrderProductRepository;
    private CouponRepository couponRepository;
    private NonMemberOrderRepository nonMemberOrderRepository;
    private NonMemberOrderProduct nonMemberOrderProduct;
    private NonMemberPaymentRepository nonMemberPaymentRepository;
    private NonMemberOrderProductRepository nonMemberOrderProductRepository;

    @Transactional
    public Header<MemberOrderApiResponse> memberOrder(Header<MemberOrderApiRequest> request){
        MemberOrderApiRequest memberOrderApiRequest = request.getData();

        Member member = memberRepository.findById(memberOrderApiRequest.getMemIdx()).get();

        MemberOrder memberOrder = MemberOrder.builder()
                .member(member)
                .dawnFlag(memberOrderApiRequest.getDawnFlag())
                .alarm(memberOrderApiRequest.getAlarm())
                .zipcode(memberOrderApiRequest.getZipcode())
                .address1(memberOrderApiRequest.getAddress1())
                .address2(memberOrderApiRequest.getAddress2())
                .requestType(memberOrderApiRequest.getRequestType())
                .requestMemo1(memberOrderApiRequest.getRequestMemo1())
                .requestMemo2(memberOrderApiRequest.getRequestMemo2())
                .build();

        if (memberOrderApiRequest.getCpIdx() != null){
            Coupon coupon = couponRepository.findById(memberOrderApiRequest.getCpIdx()).get();
            coupon.setUsedFlag(Flag.TRUE);
            memberOrder.setCoupon(coupon);
            couponRepository.save(coupon);
        }

        MemberPayment memberPayment = MemberPayment.builder()
                .order(memberOrder)
                .member(member)
                .price(memberOrderApiRequest.getPrice())
                .state(memberOrder.getState())
                .paymentType(memberOrderApiRequest.getPaymentType())
                .num(memberOrderApiRequest.getCardNum())
                .build();

        memberPayment = memberPaymentRepository.save(memberPayment);
        memberOrder.setPayment(memberPayment);

        memberOrderRepository.save(memberOrder);

        List<Long> proList= memberOrderApiRequest.getProIdxList();
        List<Integer> proCountList = memberOrderApiRequest.getProCountList();

        for (int i = 0; i < proList.size(); i++){
            Product product = productRepository.findById(proList.get(i)).get();
            MemberOrderProduct memberOrderProduct = MemberOrderProduct.builder()
                    .order(memberOrder)
                    .product(product)
                    .proCount(proCountList.get(i))
                    .proPrice(product.getPrice())
                    .build();
            memberOrderProductRepository.save(memberOrderProduct);
        }

        return Header.OK();
    }

    @Transactional
    public Header<NonMemberOrderApiResponse> nonMemberOrder(Header<NonMemberOrderApiRequest> request){
        NonMemberOrderApiRequest nonMemberOrderApiRequest = request.getData();

        NonMemberOrder nonMemberOrder = NonMemberOrder.builder()
                .name(nonMemberOrderApiRequest.getName())
                .hp(nonMemberOrderApiRequest.getHp())
                .alarm(nonMemberOrderApiRequest.getAlarm())
                .zipcode(nonMemberOrderApiRequest.getZipcode())
                .address1(nonMemberOrderApiRequest.getAddress1())
                .address2(nonMemberOrderApiRequest.getAddress2())
                .requestType(nonMemberOrderApiRequest.getRequestType())
                .requestMemo1(nonMemberOrderApiRequest.getRequestMemo1())
                .requestMemo2(nonMemberOrderApiRequest.getRequestMemo2())
                .build();

        NonMemberPayment nonMemberPayment = NonMemberPayment.builder()
                .order(nonMemberOrder)
                .price(nonMemberOrderApiRequest.getPrice())
                .state(nonMemberOrderApiRequest.getState())
                .paymentType(nonMemberOrderApiRequest.getPaymentType())
                .num(nonMemberOrderApiRequest.getNum())
                .build();

        nonMemberPayment = nonMemberPaymentRepository.save(nonMemberPayment);
        nonMemberOrder.setPayment(nonMemberPayment);

        nonMemberOrderRepository.save(nonMemberOrder);

        Long[] proIdx = nonMemberOrderApiRequest.getProIdx();
        Integer[] proCount = nonMemberOrderApiRequest.getProCount();
        Integer[] proPrice = nonMemberOrderApiRequest.getProPrice();

        for (int i = 0; i < nonMemberOrderApiRequest.getProIdx().length; i++){
            Product product = productRepository.findById(proIdx[i]).get();
            NonMemberOrderProduct nonMemberOrderProduct = NonMemberOrderProduct.builder()
                    .order(nonMemberOrder)
                    .product(product)
                    .proCount(proCount[i])
                    .proPrice(proPrice[i])
                    .build();
            nonMemberOrderProductRepository.save(nonMemberOrderProduct);
        }

        return Header.OK();
    }
}
