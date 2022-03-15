package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.MemberOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberOrderRepository extends JpaRepository<MemberOrder, Long> {
    Optional<MemberOrder>findById(Long id);
    Long countAllByStateAndRegdateBetween(Integer state, LocalDateTime start, LocalDateTime end);
    Long countAllByRegdateBetweenAndStateIsGreaterThanEqual(LocalDateTime start, LocalDateTime end, Integer integer);
    Long countAllByRegdateBetweenAndStateIsLessThanEqual(LocalDateTime start, LocalDateTime end, Integer integer);
    Long countAllByRegdateBetween(LocalDateTime localDateTime1, LocalDateTime localDateTime2);
}
