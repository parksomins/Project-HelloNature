package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Like;
import com.hellonature.hellonature_back.model.entity.Magazine;
import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.entity.Product;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.enumclass.MagazineType;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.MagazineApiRequest;
import com.hellonature.hellonature_back.model.network.response.MagazineApiResponse;
import com.hellonature.hellonature_back.model.network.response.MagazineDetailResponse;
import com.hellonature.hellonature_back.repository.LikeRepository;
import com.hellonature.hellonature_back.repository.MagazineRepository;
import com.hellonature.hellonature_back.repository.MemberRepository;
import com.hellonature.hellonature_back.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MagazineService {

    private final EntityManager em;
    private final MagazineRepository magazineRepository;
    private final ProductRepository productRepository;
    private final LikeRepository likeRepository;
    private final FileService fileService;
    private final MemberRepository memberRepository;


    public Header<MagazineApiResponse> create(MagazineApiRequest magazineApiRequest, List<MultipartFile> multipartFiles){
        List<String> pathList = fileService.imagesUploads(multipartFiles, "magazine");

        Magazine magazine = Magazine.builder()
                .title(magazineApiRequest.getTitle())
                .img(pathList.get(0))
                .des(magazineApiRequest.getDes())
                .content(magazineApiRequest.getContent())
                .type(magazineApiRequest.getType())
                .cookTime(magazineApiRequest.getCookTime())
                .cookKcal(magazineApiRequest.getCookKcal())
                .cookLevel(magazineApiRequest.getCookLevel())
                .cookIngredient(magazineApiRequest.getCookIngredient())
                .ingreList(magazineApiRequest.getIngreList())
                .relList(magazineApiRequest.getRelList())
                .build();
        magazineRepository.save(magazine);
        return Header.OK();
    }


    public Header<MagazineApiResponse> read(Long id) {
        return magazineRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("No data")
                );
    }


    public Header<MagazineApiResponse> update(Header<MagazineApiRequest> request, List<MultipartFile> multipartFiles) {
        List<String> pathList = fileService.imagesUploads(multipartFiles, "magazine");
        MagazineApiRequest magazineApiRequest = request.getData();
        Optional<Magazine> optional = magazineRepository.findById(magazineApiRequest.getIdx());
        return optional.map(magazine -> {
                    magazine.setShowFlag(magazineApiRequest.getShowFlag());
                    magazine.setImg(pathList.get(0));
                    magazine.setTitle(magazineApiRequest.getTitle());
                    magazine.setDes(magazineApiRequest.getDes());
                    magazine.setContent(magazineApiRequest.getContent());
                    magazine.setType(magazineApiRequest.getType());
                    magazine.setCookTime(magazineApiRequest.getCookTime());
                    magazine.setCookKcal(magazineApiRequest.getCookKcal());
                    magazine.setCookLevel(magazineApiRequest.getCookLevel());
                    magazine.setCookIngredient(magazineApiRequest.getCookIngredient());

                    return magazine;
                }).map(magazineRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }


    public Header delete(Long id) {
        Optional<Magazine> optional = magazineRepository.findById(id);

        return optional.map(magazine -> {
            magazineRepository.delete(magazine);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private MagazineApiResponse response(Magazine magazine){
        MagazineApiResponse magazineApiResponse = MagazineApiResponse.builder()
                .idx(magazine.getIdx())
                .showFlag(magazine.getShowFlag())
                .img(magazine.getImg())
                .title(magazine.getTitle())
                .des(magazine.getDes())
                .like(magazine.getLike())
                .content(magazine.getContent())
                .type(magazine.getType())
                .cookTime(magazine.getCookTime())
                .cookKcal(magazine.getCookKcal())
                .cookLevel(magazine.getCookLevel())
                .cookIngredient(magazine.getCookIngredient())
                .regdate(magazine.getRegdate())
                .build();
        System.out.println(magazineApiResponse);
        return magazineApiResponse;
    }

    public Header<List<MagazineApiResponse>> list(Long cateIdx, String title, String dateStart, String dateEnd, MagazineType magazineType, Integer startPage){
        String jpql = "select m from Magazine m";
        boolean check = false;

        if(cateIdx != null || title != null || dateStart != null || dateEnd != null || magazineType != null){
            jpql += " where";
            if(cateIdx != null){
                jpql += " cate_idx = :cateIdx";
                check = true;
            }
            if (title != null){
                if (check) jpql += " and";
                jpql += " title like :title";
                check = true;
            }
            if (dateStart != null){
                if(check) jpql += " and";
                jpql += " TO_char(regdate, 'YYYY-MM-DD') >= :dateStart";
                check = true;
            }
            if(dateEnd != null){
                if (check) jpql += " and";
                jpql += " TO_char(regdate, 'YYYY-MM-DD') <= :dateEnd";
            }
            if (magazineType != null){
                if (check) jpql += " and";
                jpql += " mg_type = :magazineType";
            }
        }

        jpql += " order by idx desc";

        TypedQuery<Magazine> query = em.createQuery(jpql, Magazine.class);
        if (cateIdx != null) query = query.setParameter("cateIdx", cateIdx);
        if (title != null) query = query.setParameter("title", "%"+title+"%");
        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);
        if (magazineType != null) query = query.setParameter("magazineType", magazineType.getId());

        List<Magazine> result = query.getResultList();
        int count = 10;
        int start = count * startPage;
        int end = Math.min(result.size(), start + count);

        Pagination pagination = new Pagination().builder()
                .totalPages(result.size() % count == 0 ? result.size()/count - 1 : result.size()/count)
                .totalElements((long) result.size())
                .currentPage(startPage)
                .currentElements(end - start)
                .build();

        List<MagazineApiResponse> newList = new ArrayList<>();
        for (Magazine magazine: result.subList(start, end)) {
            newList.add(response(magazine));
        }

        return Header.OK(newList, pagination);
    }

    @Transactional
    public Header<MagazineDetailResponse> detail(Long idx, Long memIdx){
        Optional<Magazine> optionalMagazine = magazineRepository.findById(idx);
        Magazine magazine = optionalMagazine.get();
        List<Product> ingreList = new ArrayList<>();
        List<Product> relList = new ArrayList<>();

        if (magazine.getIngreList() != null){
            List<Long> list1 = Arrays.stream(magazine.getIngreList().split("-")).map(Long::parseLong).collect(Collectors.toList());
            ingreList = productRepository.findAllByIdxIn(list1);
        }
        if (magazine.getRelList() != null){
            List<Long> list2 = Arrays.stream(magazine.getRelList().split("-")).map(Long::parseLong).collect(Collectors.toList());
            relList = productRepository.findAllByIdxIn(list2);
        }

        MagazineDetailResponse magazineDetailResponse = MagazineDetailResponse.builder()
                .idx(magazine.getIdx())
                .img(magazine.getImg())
                .title(magazine.getTitle())
                .des(magazine.getDes())
                .content(magazine.getContent())
                .like(Long.valueOf(magazine.getLike()))
                .likeFlag(findLike(magazine, memIdx) ? Flag.TRUE : Flag.FALSE)
                .type(magazine.getType())
                .cookTime(magazine.getCookTime())
                .cookKcal(magazine.getCookKcal())
                .cookLevel(magazine.getCookLevel())
                .cookIngredient(magazine.getCookIngredient())
                .regdate(magazine.getRegdate())
                .build();

        for (Product product: ingreList) {
            magazineDetailResponse.addIngreList(product.getIdx(), product.getName(), product.getImg1(), product.getNetPrice(), product.getSalePrice(), product.getBestFlag());
        }

        for (Product product: relList){
            magazineDetailResponse.addRelList(product.getIdx(), product.getName(), product.getImg1(), product.getNetPrice(), product.getSalePrice(), product.getBestFlag());
        }

        return Header.OK(magazineDetailResponse);
    }

    public Header<List<MagazineApiResponse>> userList(MagazineType magazineType, Integer page){
        List<Magazine> magazines = magazineRepository.findAllByTypeAndAndShowFlagOrderByIdxDesc(magazineType, Flag.TRUE);
        List<MagazineApiResponse> magazineApiResponses = new ArrayList<>();

        int count = 8;
        int start = count * page;
        int end = Math.min(magazines.size(), start + count);

        for (Magazine magazine: magazines.subList(start, end)){
            magazineApiResponses.add(response(magazine));
        }

        Pagination pagination = new Pagination().builder()
                .totalPages(magazines.size() / count)
                .totalElements((long) magazines.size())
                .currentPage(page)
                .currentElements(end - start)
                .build();

        return Header.OK(magazineApiResponses, pagination);
    }

    private boolean findLike(Magazine magazine, Long memIdx){
        if (memIdx == null) return false;
        Optional<Member> optionalMember = memberRepository.findById(memIdx);
        if (optionalMember.isEmpty()) return false;
        Optional<Like> optionalLike = likeRepository.findByMemberAndMagazine(optionalMember.get(), magazine);
        return optionalLike.isPresent();
    }

    @Transactional
    public Header deletePost(List<Long> idx) {
        magazineRepository.deleteAllByIdxIn(idx);
        return Header.OK();
    }

}

