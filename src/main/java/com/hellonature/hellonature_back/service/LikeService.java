package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Like;
import com.hellonature.hellonature_back.model.entity.Magazine;
import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.entity.Product;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.LikeApiRequest;
import com.hellonature.hellonature_back.model.network.response.LikeApiResponse;
import com.hellonature.hellonature_back.model.network.response.MagazineApiResponse;
import com.hellonature.hellonature_back.model.network.response.ProductApiResponse;
import com.hellonature.hellonature_back.repository.LikeRepository;
import com.hellonature.hellonature_back.repository.MagazineRepository;
import com.hellonature.hellonature_back.repository.MemberRepository;
import com.hellonature.hellonature_back.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final MagazineRepository magazineRepository;

    public Header<LikeApiResponse> create(Header<LikeApiRequest> request) {
        LikeApiRequest likeApiRequest = request.getData();

        Optional<Member> optional = memberRepository.findById(likeApiRequest.getMemIdx());
        Member member = optional.get();

        Like like = Like.builder()
                .member(memberRepository.findById(likeApiRequest.getMemIdx()).get())
                .build();

        if(likeApiRequest.getProIdx() != null){
            Optional<Product> optionalProduct = productRepository.findById(likeApiRequest.getProIdx());
            Product product = optionalProduct.get();
            product.plusLike();
            like.setProduct(product);
        }
        else{
            Optional<Magazine> optionalMagazine = magazineRepository.findById(likeApiRequest.getMgIdx());
            Magazine magazine = optionalMagazine.get();
            magazine.plusLike();
            like.setMagazine(magazine);
        }

        likeRepository.save(like);

        return Header.OK();
    }

    @Transactional
    public Header proDelete(Long memIdx, Long proIdx){
        try {
            Optional<Product> optional = productRepository.findById(proIdx);
            Product product = optional.get();
            product.minusLike();
            likeRepository.deleteByMemberAndProduct(memberRepository.findById(memIdx).get(), product);
        }catch (Exception e){
            return Header.ERROR("회원 또는 상품정보가 잘못되었습니다");
        }
        return Header.OK();
    }

    @Transactional
    public Header magDelete(Long memIdx, Long magIdx){
        try {
            Optional<Magazine> optional = magazineRepository.findById(magIdx);
            Magazine magazine = optional.get();
            magazine.minusLike();
            likeRepository.deleteByMemberAndMagazine(memberRepository.findById(memIdx).get(), magazine);
        }catch (Exception e){
            return Header.ERROR("회원 또는 매거진이 잘못되었습니다");
        }
        return Header.OK();
    }


    public Header<List<LikeApiResponse>> productList(Long memIdx){
        Optional<Member> optional = memberRepository.findById(memIdx);
        List<Like> list = likeRepository.findAllByMemberAndProductIsNotNull(optional.get());

        List<LikeApiResponse> productList = new ArrayList<>();
        for (Like like: list) {
            productList.add(productResponse(like.getProduct()));
        }

        return Header.OK(productList);
    }

    public Header<List<LikeApiResponse>> magazineList(Long memIdx){
        Optional<Member> optional = memberRepository.findById(memIdx);
        List<Like> list = likeRepository.findAllByMemberAndMagazineIsNotNull(optional.get());

        List<LikeApiResponse> magazineList = new ArrayList<>();
        for (Like like: list) {
            magazineList.add(magazineResponse(like.getMagazine()));
        }

        return Header.OK(magazineList);
    }

    private LikeApiResponse productResponse(Product product){
        return LikeApiResponse.builder()
                .proIdx(product.getIdx())
                .proName(product.getName())
                .proImg(product.getImg1())
                .proWeightSize(product.getSizeWeight())
                .proLikeCount(product.getLike())
                .build();
    }

    private LikeApiResponse magazineResponse(Magazine magazine){
        return LikeApiResponse.builder()
                .mgIdx(magazine.getIdx())
                .mgTitle(magazine.getTitle())
                .mgImg(magazine.getImg())
                .mgLikeCount(magazine.getLike())
                .build();
    }
}
