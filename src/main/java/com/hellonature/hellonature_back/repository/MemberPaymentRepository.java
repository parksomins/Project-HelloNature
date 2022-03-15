package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.MemberOrder;
import com.hellonature.hellonature_back.model.entity.MemberPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberPaymentRepository extends JpaRepository<MemberPayment, Long> {
    void deleteAllByIdxIn(List<Long> idx);
    Optional<MemberPayment> findByOrder(MemberOrder memberOrder);
}
