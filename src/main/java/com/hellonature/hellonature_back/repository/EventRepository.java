package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    void deleteAllByIdxIn(List<Long> idx);
}
