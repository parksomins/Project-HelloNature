package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.entity.ProductQuestion;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.ProductQuestionApiRequest;
import com.hellonature.hellonature_back.model.network.response.MyPageProductQuestionResponse;
import com.hellonature.hellonature_back.model.network.response.ProductQuestionApiResponse;
import com.hellonature.hellonature_back.model.network.response.ProductQuestionListResponse;
import com.hellonature.hellonature_back.repository.MemberRepository;
import com.hellonature.hellonature_back.repository.ProductQuestionRepository;
import com.hellonature.hellonature_back.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductQuestionService  {
    private final EntityManager em;
    private final ProductQuestionRepository productQuestionRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;


    public Header<ProductQuestionApiResponse> create(Header<ProductQuestionApiRequest> request) {
//        List<String> pathList = fileService.imagesUploads(multipartFiles, "productQuestion");
        ProductQuestionApiRequest productQuestionApiRequest = request.getData();

        ProductQuestion productQuestion = ProductQuestion.builder()
                .member(memberRepository.findById(productQuestionApiRequest.getMemIdx()).get())
                .product(productRepository.findById(productQuestionApiRequest.getProIdx()).get())
                .content(productQuestionApiRequest.getContent())
//                .img(pathList.get(0))
                .build();
        productQuestionRepository.save(productQuestion);
        return Header.OK();
    }


    public Header<ProductQuestionApiResponse> read(Long id) {
        return productQuestionRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }


    public Header<ProductQuestionApiResponse> update(Header<ProductQuestionApiRequest> request) {
//        List<String> pathList = fileService.imagesUploads(multipartFiles, "productQuestion");
        ProductQuestionApiRequest productQuestionApiRequest = request.getData();
        Optional<ProductQuestion> optional = productQuestionRepository.findById(productQuestionApiRequest.getIdx());
        return optional.map(productQuestion -> {
                    productQuestion.setMember(memberRepository.findById(productQuestionApiRequest.getMemIdx()).get());
                    productQuestion.setProduct(productRepository.findById(productQuestionApiRequest.getProIdx()).get());
                    productQuestion.setContent(productQuestionApiRequest.getContent());
                    productQuestion.setAnsFlag(productQuestionApiRequest.getAnsFlag());
                    productQuestion.setAnsContent(productQuestionApiRequest.getAnsContent());
//                    productQuestion.setImg(pathList.get(0));
                    productQuestion.setAnsDate(productQuestionApiRequest.getAnsDate());

                    return productQuestion;
                }).map(productQuestionRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }


    public Header delete(Long id) {
        Optional<ProductQuestion> optional = productQuestionRepository.findById(id);
        return optional.map(productQuestion -> {
            productQuestionRepository.delete(productQuestion);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("No data"));
    }

    private ProductQuestionApiResponse response(ProductQuestion productQuestion){
        return ProductQuestionApiResponse.builder()
                .idx(productQuestion.getIdx())
                .memIdx(productQuestion.getMember().getIdx())
                .memName(productQuestion.getMember().getName())
                .proIdx(productQuestion.getProduct().getIdx())
                .proName(productQuestion.getProduct().getProName())
                .content(productQuestion.getContent())
                .ansFlag(productQuestion.getAnsFlag())
                .ansContent(productQuestion.getAnsContent())
                .ansDate(productQuestion.getAnsDate())
                .regdate(productQuestion.getRegdate())
                .img(productQuestion.getImg())
                .build();
    }

    public Header<List<ProductQuestionListResponse>> list(Flag ansFlag, String content, String name, String dateStart, String dateEnd, Integer startPage){
        String jpql = "select pq from ProductQuestion pq join pq.member m";
        boolean check = false;

        if(ansFlag!= null || content != null || name != null || dateStart != null || dateEnd != null){
            jpql += " where";
            if(ansFlag != null){
                jpql += " ans_flag = :ansFlag";
                check = true;
            }
            if (content != null){
                if (check) jpql += " and";
                jpql += " content like :content";
                check = true;
            }
            if (name != null){
                if (check) jpql += " and";
                jpql += " m.name like :name";
                check = true;
            }
            if (dateStart != null){
                if(check) jpql += " and";
                jpql += " TO_char(pq.regdate, 'YYYY-MM-DD') >= :dateStart";
                check = true;
            }
            if(dateEnd != null){
                if (check) jpql += " and";
                jpql += " TO_char(pq.regdate, 'YYYY-MM-DD') <= :dateEnd";
            }
        }

        jpql += " order by pq.idx desc";
        TypedQuery<ProductQuestion> query = em.createQuery(jpql, ProductQuestion.class);

        if (ansFlag != null) query = query.setParameter("ansFlag", ansFlag);
        if (content != null) query = query.setParameter("content", "%"+content+"%");
        if (name != null) query = query.setParameter("name", "%" + name + "%");
        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);

        List<ProductQuestion> result = query.getResultList();

        int count = 10;
        int size = result.size();
        int start = count * startPage;
        int end = Math.min(result.size(), start + count);

        List<ProductQuestionListResponse> list = new ArrayList<>();

        for (ProductQuestion productQuestion:
                result.subList(start, end)) {
            list.add(responseList(productQuestion));
        }

        Pagination pagination = new Pagination().builder()
                .totalPages(size % count == 0 ? size / count - 1 : size / count)
                .totalElements((long) size)
                .currentPage(startPage)
                .currentElements(end - start)
                .build();

        return Header.OK(list, pagination);
    }

    private ProductQuestionListResponse responseList(ProductQuestion productQuestion){
        return ProductQuestionListResponse.builder()
                .idx(productQuestion.getIdx())
                .regdate(productQuestion.getRegdate())
                .ansFlag(productQuestion.getAnsFlag())
                .ansContent(productQuestion.getAnsContent())
                .content(productQuestion.getContent())
                .name(productQuestion.getMember().getName())
                .build();
    }

    /*public Header<List<ProductQuestionApiResponse>> search(Pageable pageable){
        Page<ProductQuestion> productQuestion = productQuestionRepository.findAll(pageable);
        List<ProductQuestionApiResponse> productQuestionApiResponseList = productQuestion.stream()
                .map(this::response)
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(productQuestion.getTotalPages()-1)
                .totalElements(productQuestion.getTotalElements())
                .currentPage(productQuestion.getNumber())
                .currentElements(productQuestion.getNumberOfElements())
                .build();
        return Header.OK(productQuestionApiResponseList, pagination);
    }*/

    public Header<List<ProductQuestionListResponse>> productDetailList(Long proIdx, Integer page){
        List<ProductQuestion> productQuestions = productQuestionRepository.findAllByProduct(productRepository.findById(proIdx).get());

        int count = 5;
        int size = productQuestions.size();
        int start = count * page;
        int end = Math.min(size, start + count);

        List<ProductQuestionListResponse> productQuestionListResponses = new ArrayList<>();

        for (ProductQuestion productQuestion: productQuestions.subList(start, end)){
            productQuestionListResponses.add(responseList(productQuestion));
        }

        Pagination pagination = Pagination.builder()
                .totalPages(size % count == 0 ? size / count - 1 : size / count)
                .totalElements((long) size)
                .currentPage(page)
                .currentElements(end - start)
                .build();

        return Header.OK(productQuestionListResponses, pagination);
    }

    public Header<List<MyPageProductQuestionResponse>> myPageList(Long memIdx){
        Optional<Member> optionalMember = memberRepository.findById(memIdx);
        if(optionalMember.isEmpty()) return Header.ERROR("회원 정보가 없습니다");

        List<ProductQuestion> productQuestions = productQuestionRepository.findAllByMember(optionalMember.get());
        List<MyPageProductQuestionResponse> myPageProductQuestionResponses = new ArrayList<>();

        for (ProductQuestion productQuestion: productQuestions){
            myPageProductQuestionResponses.add(myPageProductQuestionResponse(productQuestion));
        }

        return Header.OK(myPageProductQuestionResponses);
    }

    private MyPageProductQuestionResponse myPageProductQuestionResponse(ProductQuestion productQuestion){
        return MyPageProductQuestionResponse.builder()
                .idx(productQuestion.getIdx())
                .proIdx(productQuestion.getProduct().getIdx())
                .proName(productQuestion.getProduct().getName())
                .proImg(productQuestion.getProduct().getImg1())
                .content(productQuestion.getContent())
                .ansFlag(productQuestion.getAnsFlag())
                .ansContent(productQuestion.getAnsContent())
                .ansDate(productQuestion.getAnsDate())
                .regdate(productQuestion.getRegdate())
                .build();
    }

    public Header<ProductQuestionApiResponse> updateAns(Header<ProductQuestionApiRequest> requestHeader){
        ProductQuestionApiRequest request = requestHeader.getData();
        Optional<ProductQuestion> optional = productQuestionRepository.findById(request.getIdx());
        if (optional.isEmpty()) return Header.ERROR("후기 idx가 잘못되었습니다");
        ProductQuestion productQuestion = optional.get();

        if (request.getAnsContent() == null || request.getAnsContent().isEmpty()){
            productQuestion.setAnsContent(null);
            productQuestion.setAnsFlag(Flag.FALSE);
            productQuestion.setAnsDate(null);
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            productQuestion.setAnsContent(request.getAnsContent());
            productQuestion.setAnsFlag(Flag.TRUE);
            productQuestion.setAnsDate(LocalDate.now().format(formatter));
        }

        productQuestionRepository.save(productQuestion);

        return Header.OK();
    }
}