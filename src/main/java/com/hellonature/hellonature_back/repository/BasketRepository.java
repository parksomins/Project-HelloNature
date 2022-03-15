package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.Basket;
import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findAllByMember(Member member);
    Optional<Basket> findByMemberAndProduct(Member member, Product product);
}
