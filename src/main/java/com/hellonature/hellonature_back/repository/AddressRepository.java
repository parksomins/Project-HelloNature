package com.hellonature.hellonature_back.repository;

import com.hellonature.hellonature_back.model.entity.Address;
import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    void deleteAllByIdxIn(List<Long> idx);
    List<Address> findAllByMemberAndBaseFlag(Member member, Flag flag);
    Optional<Address> findByMemberAndBaseFlag(Member member, Flag flag);
    Optional<Address> findByMemberAndGrFlag(Member member, Flag flag);
}
