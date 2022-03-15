package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    void deleteAllByIdxIn(List<Long> idx);
    List<Faq> findAllByType(Integer type);
}
