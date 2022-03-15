package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByIdAndPassword(String id, String password);
    Optional<Admin> findById(String id);
}
