package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.HellopassPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HellopassPaymentRepository extends JpaRepository<HellopassPayment, Long> {
}
