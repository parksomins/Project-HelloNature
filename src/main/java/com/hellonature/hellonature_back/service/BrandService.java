package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Brand;
import com.hellonature.hellonature_back.model.entity.Product;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.BrandApiRequest;
import com.hellonature.hellonature_back.model.network.response.BrandApiResponse;
import com.hellonature.hellonature_back.repository.BrandRepository;
import com.hellonature.hellonature_back.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final EntityManager em;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final FileService fileService;


    public Header<BrandApiResponse> create(BrandApiRequest request, List<MultipartFile> multipartFiles) {
        List<String> pathList = fileService.imagesUploads(multipartFiles, "brand");

        Brand brand = Brand.builder()
                .name(request.getName())
                .des(request.getDes())
                .logo(pathList.get(0))
                .state(request.getState())
                .banner(pathList.get(1))
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .build();
        brandRepository.save(brand);
        return Header.OK();
    }


    public Header<BrandApiResponse> read(Long idx) {
        return brandRepository.findById(idx)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }


    public Header<BrandApiResponse> update(BrandApiRequest brandApiRequest,  List<MultipartFile> multipartFiles) {
        Optional<Brand> optional = brandRepository.findById(brandApiRequest.getIdx());

        return optional.map(brand -> {
                    brand.setName(brandApiRequest.getName());
                    brand.setDes(brandApiRequest.getDes());
                    brand.setState(brandApiRequest.getState());
                    brand.setDateStart(brandApiRequest.getDateStart());
                    brand.setDateEnd(brandApiRequest.getDateEnd());

                    if (multipartFiles != null && !multipartFiles.isEmpty()){
                        List<String> pathList = fileService.imagesUploads(multipartFiles, "brand");
                        if (pathList.get(0) != null) brand.setLogo(pathList.get(0));
                        if (pathList.get(1) != null) brand.setBanner(pathList.get(1));
                    }
                    
                    return brand;
                }).map(brandRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("수정 실패"));
    }



    public Header delete(Long idx) {
        Optional<Brand> optional = brandRepository.findById(idx);
        return optional.map(brand -> {
            brandRepository.delete(brand);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("No data"));
    }

    private BrandApiResponse response(Brand brand){
        BrandApiResponse brandApiResponse = BrandApiResponse.builder()
                .idx(brand.getIdx())
                .name(brand.getName())
                .des(brand.getDes())
                .logo(brand.getLogo())
                .state(brand.getState())
                .banner(brand.getBanner())
                .dateStart(brand.getDateStart())
                .dateEnd(brand.getDateEnd())
                .build();
        return brandApiResponse;
    }

    // 번호 제목 내용 전시상태 등록일
    public Header<List<BrandApiResponse>> list(String name, Integer state, String dateStart, String dateEnd, Integer startPage, Integer count){
        String jpql = "select b from Brand b";
        boolean check = false;

        if(name != null || state != null  ||  dateStart != null || dateEnd != null){
            jpql += " where";
            if(name != null){
                jpql += " br_name like :name";
                check = true;
            }
            if (state != null){
                if(check) jpql += " and";
                jpql += " br_state = :state";
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
        }


        jpql += " order by idx desc";
        TypedQuery<Brand> query = em.createQuery(jpql, Brand.class);

        if (name != null) query = query.setParameter("name", "%"+name+"%");
        if (state != null) query = query.setParameter("state", state);
        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);

        List<Brand> result = query.getResultList();

        int size = result.size();
        int start = (count * startPage);
        int end = Math.min(size, start + count);

        List<BrandApiResponse> list = new ArrayList<>();

        for (Brand brand: result.subList(start, end)) {
            List<Product> products = productRepository.findAllByBrand(brand);
            BrandApiResponse brandApiResponse = response(brand);
            brandApiResponse.setProCount(products.size());
            list.add(brandApiResponse);
        }

        Pagination pagination = new Pagination().builder()
                .totalPages( size % count == 0 ? size/count - 1 : size / count ) // 리스트가 홀수일 때
                .totalElements((long) size)
                .currentPage(startPage)
                .currentElements(end - start)
                .build();

        return Header.OK(list, pagination);
    }

    public Header<List<BrandApiResponse>> allList(){
        return Header.OK();
    }

    @Transactional
    public Header deletePost(List<Long> idx) {
        brandRepository.deleteAllByIdxIn(idx);
        return Header.OK();
    }
}
