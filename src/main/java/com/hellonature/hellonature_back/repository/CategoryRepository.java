package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.Category;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<List<Category>> findAllByRootIdxIsNullAndLifeFlagOrderByIdx(Flag lifeFlag);
    Optional<List<Category>> findAllByRootIdxAndLifeFlag(Long rootIdx, Flag lifeFlag);
    List<Category> findAllByRootIdx(Long rootIdx);
}
