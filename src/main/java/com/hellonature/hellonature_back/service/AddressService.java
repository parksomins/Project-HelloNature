package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Address;
import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.AddressApiRequest;
import com.hellonature.hellonature_back.model.network.response.AddressApiResponse;
import com.hellonature.hellonature_back.repository.AddressRepository;
import com.hellonature.hellonature_back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService extends BaseService<AddressApiRequest, AddressApiResponse, Address> {

    private final EntityManager em;
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Header<AddressApiResponse> create(Header<AddressApiRequest> request) {

        AddressApiRequest addressApiRequest = request.getData();

        Address address = Address.builder()
                .zipcode(addressApiRequest.getZipcode())
                .addr1(addressApiRequest.getAddr1())
                .addr2(addressApiRequest.getAddr2())
                .addrName(addressApiRequest.getAddrName())
                .name(addressApiRequest.getName())
                .dawnFlag(addressApiRequest.getDawnFlag())
                .baseFlag(addressApiRequest.getBaseFlag())
                .hp(addressApiRequest.getHp())
                .member(memberRepository.findById(addressApiRequest.getMemIdx()).get())
                .requestMemo1(addressApiRequest.getRequestMemo1())
                .requestMemo2(addressApiRequest.getRequestMemo2())
                .requestType(addressApiRequest.getRequestType())
                .build();

        if (address.getBaseFlag() == Flag.TRUE){
            Address baseAddress = addressRepository.findById(addressApiRequest.getBaseIdx()).get();
            baseAddress.setBaseFlag(Flag.FALSE);
            addressRepository.save(baseAddress);
        }

        addressRepository.save(address);
        return Header.OK();
    }

    @Override
    public Header<AddressApiResponse> read(Long idx) {
        return addressRepository.findById(idx)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("No data")
                );
    }

    @Override
    @Transactional
    public Header<AddressApiResponse> update(Header<AddressApiRequest> request) {
        AddressApiRequest addressApiRequest = request.getData();
        Optional<Address> optional = addressRepository.findById(addressApiRequest.getIdx());
        return optional.map(address -> {
            address.setIdx(addressApiRequest.getIdx());
            address.setMember(memberRepository.findById(addressApiRequest.getMemIdx()).get());
            address.setName(addressApiRequest.getName());
            address.setHp(addressApiRequest.getHp());
            address.setAddrName(addressApiRequest.getAddrName());
            address.setZipcode(addressApiRequest.getZipcode());
            address.setAddr1(addressApiRequest.getAddr1());
            address.setAddr2(addressApiRequest.getAddr2());
            address.setDawnFlag(isSeoul(addressApiRequest.getAddr1()) ? Flag.TRUE : Flag.FALSE);
            address.setRequestMemo1(addressApiRequest.getRequestMemo1());
            address.setRequestMemo2(addressApiRequest.getRequestMemo2());
            address.setRequestType(addressApiRequest.getRequestType());

            if (addressApiRequest.getBaseIdx() != null && addressApiRequest.getBaseFlag() != null && addressApiRequest.getBaseFlag() == Flag.TRUE){
                address.setBaseFlag(Flag.TRUE);
                Address baseAddress = addressRepository.findById(addressApiRequest.getBaseIdx()).get();
                baseAddress.setBaseFlag(Flag.FALSE);
                addressRepository.save(baseAddress);
            }

            return address;
        }).map(addressRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("수정 실패")
                );
    }

    @Override
    public Header delete(Long idx) {
        Optional<Address> optional = addressRepository.findById(idx);
        return optional.map(address -> {
            addressRepository.delete(address);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("삭제 실패"));
    }

    private AddressApiResponse response(Address address){
        return AddressApiResponse.builder()
                .idx(address.getIdx())
                .memIdx(address.getMember().getIdx())
                .memEmail(address.getMember().getEmail())
                .memName(address.getName())
                .memHp(address.getHp())
                .name(address.getName())
                .hp(address.getHp())
                .addrName(address.getAddrName())
                .zipcode(address.getZipcode())
                .addr1(address.getAddr1())
                .addr2(address.getAddr2())
                .dawnFlag(address.getDawnFlag())
                .greenFlag(address.getGrFlag())
                .baseFlag(address.getBaseFlag())
                .requestMemo1(address.getRequestMemo1())
                .requestMemo2(address.getRequestMemo2())
                .requestType(address.getRequestType())
                .regdate(address.getRegdate())
                .build();
    }

    private Boolean isSeoul(String addr){
        String[] temp = addr.split(" ");
        return temp[0].equals("서울시") || temp[0].equals("서울");
    }

    public Header<List<AddressApiResponse>> list(String name, Integer startPage){
        String jpql = "select a from Address a";

        if(name != null ) {
            jpql += " where";
            if (name != null) {
                jpql += " maddr_name = :name";
            }
        }

        jpql += " order by a.idx desc";System.out.println(jpql);
        TypedQuery<Address> query = em.createQuery(jpql, Address.class);
        if (name != null) query = query.setParameter("name", name);

        List<Address> result = query.getResultList();
        System.out.println(result);
        System.out.println(result.stream().map(question -> {
                    System.out.println(question.getMember());
                    return question;
                })
        );

        int count = 10;
        int size = result.size();
        int start = (count * startPage);
        int end = Math.min(result.size(), start + count);

        Pagination pagination = new Pagination().builder()
                .totalPages(size % count == 0 ? size/count - 1 : size/count  )
                .totalElements((long) size)
                .currentPage(startPage+1)
                .currentElements(size)
                .build();
        
        return Header.OK(result.subList(start, end).stream().map(this::response).collect(Collectors.toList()), pagination);
    }

    @Transactional
    public Header deletePost(List<Long> idx) {
        addressRepository.deleteAllByIdxIn(idx);
        return Header.OK();
    }

    public Header<List<AddressApiResponse>> userList(Long idx){
        Member member = memberRepository.findById(idx).get();
        List<Address> addresses = addressRepository.findAllByMemberAndBaseFlag(member, Flag.FALSE);
        List<AddressApiResponse> list = new ArrayList<>();
        list.add(response(addressRepository.findByMemberAndBaseFlag(member, Flag.TRUE).get()));
        for (Address address: addresses){
            list.add(response(address));
        }
        return Header.OK(list);
    }
}
