package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.Coupon;
import com.hellonature.hellonature_back.model.entity.CouponType;
import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Long countAllByMemberAndUsedFlag(Member member, Flag usedFlag);
    List<Coupon> findAllByMemberAndUsedFlagOrderByIdxDesc(Member member, Flag usedFlag);
    Optional<Coupon> findByCouponType(CouponType couponType);
}
