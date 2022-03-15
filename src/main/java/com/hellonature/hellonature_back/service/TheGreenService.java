package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Address;
import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.entity.MemberPayment;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.TheGreenApiRequest;
import com.hellonature.hellonature_back.repository.AddressRepository;
import com.hellonature.hellonature_back.repository.MemberPaymentRepository;
import com.hellonature.hellonature_back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheGreenService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;
    private final MemberPaymentRepository memberPaymentRepository;

    @Transactional
    public Header create(Header<TheGreenApiRequest> theGreenApiRequestHeader){

        TheGreenApiRequest request = theGreenApiRequestHeader.getData();

        Optional<Member> optionalMember = memberRepository.findById(request.getMemIdx());
        Optional<Address> optionalAddress = addressRepository.findById(request.getAddrIdx());

        if (optionalMember.isEmpty()) return Header.ERROR("회원 정보가 잘못되었습니다");
        if (optionalAddress.isEmpty()) return Header.ERROR("주소지가 잘못되었습니다");

        Member member = optionalMember.get();
        Address address = optionalAddress.get();

        member.setGreenFlag(Flag.TRUE);
        address.setGrFlag(Flag.TRUE);

        memberRepository.save(member);
        addressRepository.save(address);

        MemberPayment memberPayment = MemberPayment.builder()
                .member(member)
                .price(5000)
                .state(1)
                .paymentType(3)
                .num(request.getMpaymNum())
                .build();

        memberPaymentRepository.save(memberPayment);

        return Header.OK();
    }

    @Transactional
    public Header delete(Long memIdx){

        Optional<Member> optionalMember = memberRepository.findById(memIdx);
        if (optionalMember.isEmpty()) return Header.ERROR("회원 정보가 없습니다");

        Member member = optionalMember.get();

        Optional<Address> optionalAddress = addressRepository.findByMemberAndGrFlag(member, Flag.TRUE);
        if (optionalAddress.isEmpty()) return Header.ERROR("주소지가 업습니다");

        Address address = optionalAddress.get();

        member.setGreenFlag(Flag.FALSE);
        address.setGrFlag(Flag.FALSE);

        memberRepository.save(member);
        addressRepository.save(address);

        return Header.OK();
    }

    public Header<Flag> check(Long memIdx){
        Optional<Member> optionalMember = memberRepository.findById(memIdx);
        if (optionalMember.isEmpty()) return Header.ERROR("회원 정보가 없습니다");

        Member member = optionalMember.get();

        Optional<Address> optionalAddress = addressRepository.findByMemberAndGrFlag(member, Flag.TRUE);
        return optionalAddress.isEmpty() ? Header.OK(Flag.FALSE) : Header.OK(Flag.TRUE);
    }
}
