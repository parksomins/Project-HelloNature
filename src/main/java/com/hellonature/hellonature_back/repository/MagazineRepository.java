package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.Magazine;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.enumclass.MagazineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    Optional<Magazine> findById(Long id);
    List<Magazine> findAllByTypeAndAndShowFlagOrderByIdxDesc(MagazineType magazineType, Flag flag);
    Long countAllByShowFlag(Flag flag);
    void deleteAllByIdxIn(List<Long> idx);

}