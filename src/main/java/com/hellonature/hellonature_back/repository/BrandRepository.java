package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Long countAllByState(Integer state);
    void deleteAllByIdxIn(List<Long> idx);
}
