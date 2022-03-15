package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.Card;
import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByMemberOrderByBaseFlagDescIdxAsc(Member member);
    Optional<Card> findByMemberAndBaseFlag(Member member, Flag baseFlag);
    List<Card> findAllByMember(Member member);
}
