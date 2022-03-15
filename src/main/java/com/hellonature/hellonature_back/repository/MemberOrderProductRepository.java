package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.MemberOrder;
import com.hellonature.hellonature_back.model.entity.MemberOrderProduct;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberOrderProductRepository extends JpaRepository<MemberOrderProduct, Long> {
    Optional<MemberOrderProduct> findById(Long id);
    List<MemberOrderProduct> findAllByOrder(MemberOrder order);
    List<MemberOrderProduct> findAllByOrderIn(List<MemberOrder> orders, Sort sort);

}
