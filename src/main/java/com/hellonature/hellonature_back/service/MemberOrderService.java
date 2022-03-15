package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.*;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.MemberOrderApiRequest;
import com.hellonature.hellonature_back.model.network.response.AddressApiResponse;
import com.hellonature.hellonature_back.model.network.response.MemberCouponApiResponse;
import com.hellonature.hellonature_back.model.network.response.MemberOrderApiResponse;
import com.hellonature.hellonature_back.model.network.response.MemberOrderLoadResponse;
import com.hellonature.hellonature_back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberOrderService extends BaseService<MemberOrderApiRequest, MemberOrderApiResponse, MemberOrder> {

    private final MemberOrderRepository memberOrderRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberPaymentRepository memberPaymentRepository;
    private final ProductRepository productRepository;
    private final MemberOrderProductRepository memberOrderProductRepository;
    private final HellocashRepository hellocashRepository;
    private final AddressRepository addressRepository;
    private final BasketRepository basketRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductReviewRepository productReviewRepository;

    @Override
    @Transactional
    public Header<MemberOrderApiResponse> create(Header<MemberOrderApiRequest> request) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Hellocash hellocash;

        MemberOrderApiRequest memberOrderApiRequest = request.getData();

        Optional<Member> optionalMember = memberRepository.findById(memberOrderApiRequest.getMemIdx());
        if (optionalMember.isEmpty()) return Header.ERROR("회원 정보가 잘못되었습니다");

        Member member = optionalMember.get();

//       사용한 쿠폰처리
        Coupon coupon = null;
        if (memberOrderApiRequest.getCpIdx() != null) {
            Optional<Coupon> optionalCoupon = couponRepository.findById(memberOrderApiRequest.getCpIdx());
            if (optionalCoupon.isEmpty()) return Header.ERROR("존재하지 않는 쿠폰입니다");
            coupon = optionalCoupon.get();
            coupon.setUsedFlag(Flag.TRUE);
            coupon = couponRepository.save(coupon);
        }

//        주문 정보 생성
        MemberOrder memberOrder = MemberOrder.builder()
                .state(1)
                .dawnFlag(memberOrderApiRequest.getDawnFlag())
                .recName(memberOrderApiRequest.getRecName())
                .recHp(memberOrderApiRequest.getRecHp())
                .alarm(memberOrderApiRequest.getAlarm())
                .zipcode(memberOrderApiRequest.getZipcode())
                .address1(memberOrderApiRequest.getAddress1())
                .address2(memberOrderApiRequest.getAddress2())
                .requestType(memberOrderApiRequest.getRequestType())
                .requestMemo1(memberOrderApiRequest.getRequestMemo1())
                .requestMemo2(memberOrderApiRequest.getRequestMemo2())
                .greenFlag(memberOrderApiRequest.getGreenFlag())
                .member(member)
                .coupon(coupon)
                .build();
        MemberOrder newMemberOrder = memberOrderRepository.save(memberOrder);

//        주문 상품 목록 생성, 장바구니 삭제, 구매내역 생성, 리뷰 생성
        List<Product> products = productRepository.findAllByIdxIn(memberOrderApiRequest.getProIdxList());
        for (int i = 0; i < products.size(); i++){
            MemberOrderProduct memberOrderProduct = MemberOrderProduct.builder()
                    .order(newMemberOrder)
                    .product(products.get(i))
                    .proPrice(products.get(i).getPrice())
                    .proCount(memberOrderApiRequest.getProCountList().get(i))
                    .build();
            MemberOrderProduct newMemberOrderProduct = memberOrderProductRepository.save(memberOrderProduct);

            Optional<Basket> optionalBasket = basketRepository.findByMemberAndProduct(member, products.get(i));
            if (optionalBasket.isEmpty()) continue;
            basketRepository.delete(optionalBasket.get());

            Purchase purchase = Purchase.builder()
                    .member(member)
                    .product(products.get(i))
                    .count(memberOrderApiRequest.getProCountList().get(i))
                    .build();

            purchaseRepository.save(purchase);

            ProductReview productReview = ProductReview.builder()
                    .member(member)
                    .product(products.get(i))
                    .order(newMemberOrder)
                    .memberOrderProduct(newMemberOrderProduct)
                    .build();

            productReviewRepository.save(productReview);
        }

//        결제 내역 생성
        MemberPayment memberPayment = MemberPayment.builder()
                .order(newMemberOrder)
                .member(member)
                .price(memberOrderApiRequest.getPrice())
                .state(1)
                .paymentType(memberOrderApiRequest.getPaymentType())
                .num(memberOrderApiRequest.getCardNum())
                .build();

        MemberPayment newMemberPayment = memberPaymentRepository.save(memberPayment);
        newMemberOrder.setPayment(newMemberPayment);

//        헬로캐쉬 사용시 헬로캐쉬 감소
        if (memberOrderApiRequest.getHellocash() != 0){
            int point = memberOrderApiRequest.getHellocash();
            hellocash = Hellocash.builder()
                    .member(member)
                    .point(point)
                    .dateUsed(simpleDateFormat.format(calendar.getTime()))
                    .type(2)
                    .title("상품 구매 포인트 사용")
                    .build();

            hellocashRepository.save(hellocash);
            member.minusHelloCash(point);

            memberRepository.save(member);
        }

//        상품 구매 헬로캐쉬 적립
        calendar.add(Calendar.YEAR, 1);
        int point = (int)(memberOrderApiRequest.getPrice() * 0.01);
        hellocash = Hellocash.builder()
                .member(member)
                .point(point)
                .type(1)
                .dateVal(simpleDateFormat.format(calendar.getTime()))
                .title("상품 구매 포인트 적립")
                .build();

        hellocashRepository.save(hellocash);
        member.plusHelloCash(point);
        memberRepository.save(member);

        return Header.OK();
    }

    @Override
    public Header<MemberOrderApiResponse> read(Long id) {
        return memberOrderRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }

    @Override
    public Header<MemberOrderApiResponse> update(Header<MemberOrderApiRequest> request) {
        MemberOrderApiRequest memberOrderApiRequest = request.getData();
        Optional<MemberOrder> optional = memberOrderRepository.findById(memberOrderApiRequest.getIdx());

        return optional.map(memberOrder -> {
            memberOrder.setMember(memberRepository.findById(memberOrderApiRequest.getIdx()).get());
            memberOrder.setAlarm(memberOrderApiRequest.getAlarm());
            memberOrder.setState(memberOrderApiRequest.getState());
            memberOrder.setDawnFlag(memberOrderApiRequest.getDawnFlag());
            memberOrder.setAddress1(memberOrderApiRequest.getAddress1());
            memberOrder.setAddress2(memberOrderApiRequest.getAddress2());

            return memberOrder;

        }).map(memberOrderRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("No data"));
    }

    @Override
    public Header delete(Long id) {
        Optional<MemberOrder> optional = memberOrderRepository.findById(id);
        return optional.map(memberOrder -> {
            memberOrderRepository.delete(memberOrder);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("No data"));

    }

    private MemberOrderApiResponse response(MemberOrder memberOrder){
        return MemberOrderApiResponse.builder()
                .idx(memberOrder.getIdx())
                .dawnFlag(memberOrder.getDawnFlag())
                .alarm(memberOrder.getAlarm())
                .zipcode(memberOrder.getZipcode())
                .address1(memberOrder.getAddress1())
                .address2(memberOrder.getAddress2())
                .requestType(memberOrder.getRequestType())
                .requestMemo1(memberOrder.getRequestMemo1())
                .requestMemo2(memberOrder.getRequestMemo2())
                .memIdx(memberOrder.getMember().getIdx())
                .cpIdx(memberOrder.getCoupon() != null ? memberOrder.getCoupon().getIdx() : null)
                .mpayIdx(memberOrder.getPayment().getIdx())
                .build();
    }

    public Header<MemberOrderLoadResponse> load(Long memIdx){
        Optional<Member> optionalMember = memberRepository.findById(memIdx);
        if (optionalMember.isEmpty()) return Header.ERROR("회원 정보가 잘못되었습니다");
        Member member = optionalMember.get();

        Optional<Address> optionalAddress = addressRepository.findByMemberAndBaseFlag(member, Flag.TRUE);
        if (optionalAddress.isEmpty()) return Header.ERROR("기본 배송지가 없습니다");
        Address address = optionalAddress.get();
        AddressApiResponse addressApiResponse = AddressApiResponse.builder()
                .idx(address.getIdx())
                .memIdx(address.getMember().getIdx())
                .memEmail(address.getMember().getEmail())
                .memName(address.getName())
                .memHp(address.getHp())
                .name(address.getName())
                .hp(address.getHp())
                .addrName(address.getAddrName())
                .zipcode(address.getZipcode())
                .addr1(address.getAddr1())
                .addr2(address.getAddr2())
                .dawnFlag(address.getDawnFlag())
                .greenFlag(address.getGrFlag())
                .baseFlag(address.getBaseFlag())
                .requestMemo1(address.getRequestMemo1())
                .requestMemo2(address.getRequestMemo2())
                .requestType(address.getRequestType())
                .regdate(address.getRegdate())
                .build();

        List<Coupon> coupons = couponRepository.findAllByMemberAndUsedFlagOrderByIdxDesc(member, Flag.FALSE);
        List<MemberCouponApiResponse> memberCouponApiResponses = new ArrayList<>();

        for (Coupon coupon: coupons){
            memberCouponApiResponses.add(MemberCouponApiResponse.builder()
                            .idx(coupon.getIdx())
                            .usedFlag(coupon.getUsedFlag())
                            .dateStart(coupon.getDateStart())
                            .dateEnd(coupon.getDateEnd())
                            .regdate(coupon.getRegdate())
                            .title(coupon.getCouponType().getTitle())
                            .discount(coupon.getCouponType().getDiscount())
                            .minPrice(coupon.getCouponType().getMinPrice())
                    .build());
        }

        MemberOrderLoadResponse memberOrderLoadResponse = MemberOrderLoadResponse.builder()
                .address(addressApiResponse)
                .couponList(memberCouponApiResponses)
                .hellocash(member.getHellocash())
                .build();

        return Header.OK(memberOrderLoadResponse);
    }

    @Transactional
    public Header updateState(Long idx, Integer state){
        Optional<MemberOrder> optional = memberOrderRepository.findById(idx);
        if (optional.isEmpty()) return Header.ERROR("주문 정보가 없습니다");
        MemberOrder memberOrder = optional.get();

        memberOrder.setState(state);
        memberOrderRepository.save(memberOrder);

        if (state >= 6){
            Optional<ProductReview> optionalProductReview = productReviewRepository.findByOrder(memberOrder);
            Optional<MemberPayment> optionalMemberPayment = memberPaymentRepository.findByOrder(memberOrder);
            if (optionalProductReview.isEmpty() || optionalMemberPayment.isEmpty()) return Header.ERROR("상태를 변경할 수 없습니다");
            ProductReview productReview = optionalProductReview.get();
            productReviewRepository.delete(productReview);
            MemberPayment memberPayment = optionalMemberPayment.get();
            memberPayment.setState(2);
            memberPaymentRepository.save(memberPayment);
        }

        return Header.OK();
    }
}
