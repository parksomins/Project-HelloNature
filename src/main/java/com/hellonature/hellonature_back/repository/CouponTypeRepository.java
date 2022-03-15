package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponTypeRepository extends JpaRepository<CouponType, Long> {
    void deleteAllByIdxIn(List<Long> idx);
}
