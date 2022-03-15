package com.hellonature.hellonature_back.service;


import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.entity.Product;
import com.hellonature.hellonature_back.model.entity.ProductReview;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.ProductReviewApiRequest;
import com.hellonature.hellonature_back.model.network.response.MemberReviewResponse;
import com.hellonature.hellonature_back.model.network.response.MyPageOrderResponse;
import com.hellonature.hellonature_back.model.network.response.ProductReviewApiResponse;
import com.hellonature.hellonature_back.model.network.response.ProductReviewListResponse;
import com.hellonature.hellonature_back.repository.MemberRepository;
import com.hellonature.hellonature_back.repository.ProductRepository;
import com.hellonature.hellonature_back.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReviewService  {
    private final EntityManager em;
    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;


    public Header<ProductReviewApiResponse> create(ProductReviewApiRequest request, List<MultipartFile> multipartFiles) {
        List<String> pathList = fileService.imagesUploads(multipartFiles, "productReview");

        ProductReview productReview = ProductReview.builder()
                .member(memberRepository.findById(request.getMemIdx()).get())
                .product(productRepository.findById(request.getProIdx()).get())
                .like(request.getLike())
                .content(request.getContent())
                .files((pathList == null || pathList.isEmpty()) ? null : pathList.get(0))
                .build();
        productReviewRepository.save(productReview);
        return Header.OK();
    }


    public Header<ProductReviewApiResponse> read(Long id) {
        return productReviewRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }


    public Header<ProductReviewApiResponse> update(ProductReviewApiRequest productReviewApiRequest, List<MultipartFile> multipartFiles) {
        Optional<ProductReview> optional = productReviewRepository.findById(productReviewApiRequest.getIdx());
        return optional.map(productReview -> {

            productReview.setIdx(productReviewApiRequest.getIdx());
            productReview.setMember(memberRepository.findById(productReviewApiRequest.getMemIdx()).get());
            productReview.setProduct(productRepository.findById(productReviewApiRequest.getProIdx()).get());
            productReview.setLike(productReviewApiRequest.getLike());
            productReview.setContent(productReviewApiRequest.getContent());
            productReview.setAnsFlag(productReviewApiRequest.getAnsFlag());
            productReview.setAnsContent(productReviewApiRequest.getAnsContent());
            productReview.setAnsDate(productReviewApiRequest.getAnsDate());

            if (multipartFiles != null && !multipartFiles.isEmpty()){
                List<String> pathList = fileService.imagesUploads(multipartFiles, "productReview");
                productReview.setFiles(pathList.get(0));
            }

            return productReview;
        }).map(productReviewRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("수정 실패"));
    }


    public Header delete(Long id) {
        Optional<ProductReview> optional = productReviewRepository.findById(id);

        return optional.map(productReview -> {
            productReviewRepository.delete(productReview);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("No data"));
    }

    private ProductReviewApiResponse response(ProductReview productReview){
        return ProductReviewApiResponse.builder()
                .idx(productReview.getIdx())
                .memIdx(productReview.getMember().getIdx())
                .memName(productReview.getMember().getName())
                .proIdx(productReview.getProduct().getIdx())
                .proName(productReview.getProduct().getName())
                .like(productReview.getLike())
                .content(productReview.getContent())
                .ansFlag(productReview.getAnsFlag())
                .ansContent(productReview.getAnsContent())
                .ansDate(productReview.getAnsDate())
                .files(productReview.getFiles())
                .regdate(productReview.getRegdate())
                .build();
    }

    public Header<List<ProductReviewListResponse>> list(Flag ansFlag, Long proIdx, Integer page){
        String jpql = "select pr from ProductReview pr";
        boolean check = false;

        if(ansFlag!= null ||proIdx != null){
            jpql += " where";
            if(ansFlag != null){
                jpql += " rv_content is not null and";
                jpql += " ans_flag = :ansFlag";
                check = true;
            }

            if (proIdx != null){
                if (check) jpql += " and";
                jpql += " pro_idx = :proIdx";
            }
        }

        jpql += " order by idx desc";
        TypedQuery<ProductReview> query = em.createQuery(jpql, ProductReview.class);

        if (ansFlag != null) query = query.setParameter("ansFlag", ansFlag);
        if (proIdx != null) query = query.setParameter("proIdx", "%"+proIdx+"%");

        List<ProductReview> result = query.getResultList();

        int count = 10;

        int start = count * page;
        int end = Math.min(result.size(), start + count);

        List<ProductReviewListResponse> list = new ArrayList<>();

        for (ProductReview productReview: result.subList(start, end)) {
            list.add(responseList(productReview));
        }

        Pagination pagination = new Pagination().builder()
                .totalPages(result.size() / count)
                .totalElements((long) result.size())
                .currentPage(page)
                .currentElements(end - start)
                .build();

        return Header.OK(list, pagination);
    }

    private ProductReviewListResponse responseList(ProductReview productReview){
        return ProductReviewListResponse.builder()
                .idx(productReview.getIdx())
                .proName(productReview.getProduct().getName())
                .proIdx(productReview.getProduct().getIdx())
                .memHp(productReview.getMember().getHp())
                .memName(productReview.getMember().getName())
                .memEmail(productReview.getMember().getEmail())
                .regdate(productReview.getRegdate())
                .ansDate(productReview.getAnsDate())
                .ansFlag(productReview.getAnsFlag())
                .build();
    }

    public Header<List<ProductReviewApiResponse>> search(Pageable pageable){
        Page<ProductReview> productReview = productReviewRepository.findAll(pageable);
        List<ProductReviewApiResponse> productReviewApiResponseList = productReview.stream()
                .map(this::response)
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(productReview.getTotalPages()-1)
                .totalElements(productReview.getTotalElements())
                .currentPage(productReview.getNumber())
                .currentElements(productReview.getNumberOfElements())
                .build();
        return Header.OK(productReviewApiResponseList, pagination);
    }

    public Header<List<MemberReviewResponse>> userReviewList(Long proIdx, Integer like, Integer page){
        String jpql = "select r from ProductReview r";
        jpql += " where";
        jpql += " pro_idx = :proIdx";

        if (like != null){
            jpql += " and";
            if(like == 1) jpql += " rv_like < 4";
            else if(like == 2) jpql += " rv_like > 3 and rv_like < 7";
            else if(like == 3) jpql += " rv_like > 6";
        }

        jpql += " order by idx desc";

        TypedQuery<ProductReview> query = em.createQuery(jpql, ProductReview.class);
        query.setParameter("proIdx", proIdx);

        List<ProductReview> result = query.getResultList();

        int count = 10;
        int start = count * page;
        int end = Math.min(result.size(), start + 10);

        List<MemberReviewResponse> list = new ArrayList<>();

        for (ProductReview productReview: result.subList(start, end)){
            list.add(userReviewResponse(productReview));
        }

        Pagination pagination = Pagination.builder()
                .totalElements((long) result.size())
                .currentElements(count)
                .currentPage(page)
                .totalPages(result.size() / count)
                .build();

        return Header.OK(list, pagination);
    }

    private MemberReviewResponse userReviewResponse(ProductReview productReview){
        return MemberReviewResponse.builder()
                .idx(productReview.getIdx())
                .memIdx(productReview.getMember().getIdx())
                .memName(productReview.getMember().getName())
                .like(productReview.getLike())
                .proCount(productReview.getMemberOrderProduct().getProCount())
                .content(productReview.getContent())
                .ansFlag(productReview.getAnsFlag())
                .ansContent(productReview.getAnsContent())
                .files(productReview.getFiles())
                .ansDate(productReview.getAnsDate())
                .regdate(productReview.getRegdate())
                .build();
    }

    public Header<List<MyPageOrderResponse>> myPage(Flag flag, Long idx, String dateStart, String dateEnd){
        Member member = memberRepository.findById(idx).get();

        String[] temp;
        temp = dateStart.split("-");
        LocalDateTime start = LocalDateTime.of(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), 00, 00);
        temp = dateEnd.split("-");
        LocalDateTime end = LocalDateTime.of(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), 23, 59);

        List<ProductReview> productReviews = new ArrayList<>();
        if (flag == Flag.TRUE) productReviews = productReviewRepository.findAllByMemberAndRegdateBetweenAndContentIsNotNullOrderByIdxDesc(member, start, end);
        else if (flag == Flag.FALSE) productReviews = productReviewRepository.findAllByMemberAndRegdateBetweenAndContentIsNullOrderByIdxDesc(member, start, end);

        List<MyPageOrderResponse> list = new ArrayList<>();

        for (ProductReview productReview: productReviews){
            list.add(myPageOrderResponse(productReview));
        }

        return Header.OK(list);
    }

    public MyPageOrderResponse myPageOrderResponse(ProductReview productReview){
        Product product = productReview.getProduct();
        return MyPageOrderResponse.builder()
                .proIdx(product.getIdx())
                .proName(product.getName())
                .proImg(product.getImg1())
                .proWeightSize(product.getSizeWeight())
                .regdate(productReview.getRegdate())
                .rvIdx(productReview.getIdx())
                .ansFlag(productReview.getAnsFlag())
                .build();
    }

    public Header updateAns(Header<ProductReviewApiRequest> requestHeader){
        ProductReviewApiRequest request = requestHeader.getData();
        Optional<ProductReview> optionalProductReview = productReviewRepository.findById(request.getIdx());
        if (optionalProductReview.isEmpty()) return Header.ERROR("리뷰 idx가 잘못되었습니다");
        ProductReview productReview = optionalProductReview.get();

        if (request.getAnsContent() == null || request.getAnsContent().isEmpty()){
            productReview.setAnsContent(null);
            productReview.setAnsFlag(Flag.FALSE);
            productReview.setAnsDate(null);
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            productReview.setAnsContent(request.getAnsContent());
            productReview.setAnsFlag(Flag.TRUE);
            productReview.setAnsDate(LocalDate.now().format(formatter));
        }

        productReviewRepository.save(productReview);

        return Header.OK();
    }
}
