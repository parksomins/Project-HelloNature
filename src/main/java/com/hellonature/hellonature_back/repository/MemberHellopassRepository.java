package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.MemberHellopass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberHellopassRepository extends JpaRepository<MemberHellopass, Long> {
    Optional<MemberHellopass> findById(Long id);
}
