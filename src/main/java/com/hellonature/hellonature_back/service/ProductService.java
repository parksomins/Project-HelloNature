package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.*;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.ProductApiRequest;
import com.hellonature.hellonature_back.model.network.response.ProductApiResponse;
import com.hellonature.hellonature_back.model.network.response.ProductDetailResponse;
import com.hellonature.hellonature_back.model.network.response.ProductMagazineCreateResponse;
import com.hellonature.hellonature_back.model.network.response.ProductUserListResponse;
import com.hellonature.hellonature_back.repository.*;
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
public class ProductService{

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductReviewRepository productReviewRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final EntityManager em;
    private final FileService fileService;

    public Header<ProductApiResponse> create(ProductApiRequest request, List<MultipartFile> multipartFiles) {
        List<String> pathList = fileService.imagesUploads(multipartFiles, "product");

        Product product = Product.builder()
                .name(request.getName())
                .des(request.getDes())
                .brand(brandRepository.findById(request.getBrIdx()).get())
                .netPrice(request.getNetPrice())
                .salePrice(request.getSalePrice())
                .price(request.getSalePrice() == null ? request.getNetPrice() : request.getNetPrice() * (100 - request.getSalePrice()) / 100)
                .state(request.getState())
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .origin(request.getOrigin())
                .sizeWeight(request.getSizeWeight())
                .temp(request.getTemp())
                .count(request.getCount())
                .delivery(request.getDelivery())
                .packing(request.getPacking())
                .img1(pathList.get(0))
                .img2(pathList.get(1))
                .img3(pathList.get(2))
                .img4(pathList.get(3))
                .proDes(request.getProDes())
                .category(categoryRepository.findById(request.getCateIdx()).get())
                .proType(request.getProType())
                .proName(request.getProName())
                .foodType(request.getFoodType())
                .producer(request.getProducer())
                .location(request.getLocation())
                .dateBuilt(request.getDateBuilt())
                .dateValid(request.getDateValid())
                .build();

//        if(request.getEveCateIdx() != null) product.setEveCategory(categoryRepository.findById(request.getEveCateIdx()).get());

        productRepository.save(product);
        return Header.OK();
    }

    public Header<ProductApiResponse> read(Long id) {
        return productRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    public Header<ProductApiResponse> update(ProductApiRequest productApiRequest, List<MultipartFile> multipartFiles) {
        Optional<Product> optional = productRepository.findById(productApiRequest.getIdx());
        return optional.map(product -> {
                    product.setName(productApiRequest.getName());
                    product.setDes(productApiRequest.getDes());
                    product.setBrand(brandRepository.findById(productApiRequest.getBrIdx()).get());
                    product.setNetPrice(productApiRequest.getNetPrice());
                    product.setSalePrice(productApiRequest.getSalePrice());
                    product.setPrice(productApiRequest.getSalePrice() == null ? productApiRequest.getNetPrice() : productApiRequest.getNetPrice() * (100 - productApiRequest.getSalePrice()) / 100);
                    product.setState(productApiRequest.getState());
                    product.setDateStart(productApiRequest.getDateStart());
                    product.setDateEnd(productApiRequest.getDateEnd());
                    product.setOrigin(productApiRequest.getOrigin());
                    product.setSizeWeight(productApiRequest.getSizeWeight());
                    product.setTemp(productApiRequest.getTemp());
                    product.setCount(productApiRequest.getCount());
                    product.setDelivery(productApiRequest.getDelivery());
                    product.setPacking(productApiRequest.getPacking());
                    product.setBestFlag(productApiRequest.getBestFlag());

                    if(multipartFiles != null && !multipartFiles.isEmpty()){
                        List<String> pathList = fileService.imagesUploads(multipartFiles, "product");
                        if(productApiRequest.getImg1() != null && productApiRequest.getImg1().equals("") && pathList.get(0) != null) product.setImg1(pathList.get(0));
                        if(productApiRequest.getImg2() != null && productApiRequest.getImg2().equals("") && pathList.get(0) != null) product.setImg2(pathList.get(1));
                        if(productApiRequest.getImg3() != null && productApiRequest.getImg3().equals("") && pathList.get(0) != null) product.setImg2(pathList.get(2));
                        if(productApiRequest.getImg4() != null && productApiRequest.getImg4().equals("") && pathList.get(0) != null) product.setImg2(pathList.get(3));
                    }

                    product.setProDes(productApiRequest.getProDes());
                    product.setCategory(categoryRepository.findById(productApiRequest.getCateIdx()).get());

                    if((productApiRequest.getEveCateIdx() != null)) product.setEveCategory(categoryRepository.findById(productApiRequest.getEveCateIdx()).get());

                    product.setProType(productApiRequest.getProType());
                    product.setProName(productApiRequest.getProName());
                    product.setFoodType(productApiRequest.getFoodType());
                    product.setProducer(productApiRequest.getProducer());
                    product.setLocation(productApiRequest.getLocation());
                    product.setDateBuilt(productApiRequest.getDateBuilt());
                    product.setDateValid(productApiRequest.getDateValid());

                    return product;
                }).map(productRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("수정 실패"));

    }

    public Header delete(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        return optional.map(product -> {
            productRepository.delete(product);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("삭제 실패"));
    }

    @Transactional
    public Header deleteList(List<Long> idxList){
        try {
            productRepository.deleteAllByIdxIn(idxList);
        }catch (Exception e){
            return Header.ERROR("상품 삭제를 실패하였습니다");
        }
        return Header.OK();
    }

    private ProductApiResponse response(Product product){
        return ProductApiResponse.builder()
                .idx(product.getIdx())
                .name(product.getName())
                .des(product.getDes())
                .brIdx(product.getBrand().getIdx())
                .netPrice(product.getNetPrice())
                .salePrice(product.getSalePrice())
                .price(product.getPrice())
                .state(product.getState())
                .dateStart(product.getDateStart())
                .dateEnd(product.getDateEnd())
                .origin(product.getOrigin())
                .sizeWeight(product.getSizeWeight())
                .temp(product.getTemp())
                .count(product.getCount())
                .delivery(product.getDelivery())
                .packing(product.getPacking())
                .img1(product.getImg1())
                .img2(product.getImg2())
                .img3(product.getImg3())
                .img4(product.getImg4())
                .like(product.getLike())
                .bestFlag(product.getBestFlag())
                .proDes(product.getProDes())
                .cateIdx(product.getCategory().getIdx())
                .cateName(product.getCategory().getName())
                .eveCateIdx(product.getEveCategory() == null ? null : product.getEveCategory().getIdx())
                .proType(product.getProType())
                .proName(product.getProName())
                .foodType(product.getFoodType())
                .producer(product.getProducer())
                .location(product.getLocation())
                .dateBuilt(product.getDateBuilt())
                .dateValid(product.getDateValid())
                .regdate(product.getRegdate())
                .build();
    }

    public Header<List<ProductApiResponse>> list(Integer state, Long cateIdx, String name, Long id, Long brIdx, Integer order, Integer page){
        String jpql = "select p from Product p";
        boolean check = false;
        List<Category> categories = new ArrayList<>();

        if(state != null || cateIdx != null || name != null || id != null || brIdx != null){
            jpql += " where";
            if (cateIdx != null){
                categories = searchCategories(cateIdx);
                jpql += "(cate_idx = :cateIdx0";
                for (int i = 1; i < categories.size(); i++){
                    jpql += " or cate_idx = :cateIdx" + i;
                }
                jpql += ")";
                check = true;
            }
            if (state != null){
                if (check) jpql += " and";
                jpql += " state = :state";
                check = true;
            }
            if (name != null){
                if (check) jpql += " and";
                jpql += " title like :name";
                check = true;

            }
            if (id != null){
                if (check) jpql += " and";
                jpql += " idx = :id";
                check = true;
            }
            if (brIdx != null){
                if (check) jpql += " and";
                jpql += " br_idx = :brIdx";
            }
        }

        switch (order){
            case 1:
                jpql += " order by idx desc";
                break;
            case 2:
                jpql += " order by idx asc";
                break;
            case 3:
                jpql += " order by price desc";
                break;
            case 4:
                jpql += " order by price asc";
                break;
        }

        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        if (state != null) query = query.setParameter("state", state);
        if (name != null) query = query.setParameter("name", "%" + name + "%");
        if (id != null) query = query.setParameter("id", id);
        for (int i = 0; i < categories.size(); i++){
            query = query.setParameter("cateIdx"+i, categories.get(i).getIdx());
        }
        if (brIdx != null) query = query.setParameter("brIdx", brIdx);

        List<Product> result = query.getResultList();
        List<ProductApiResponse> list = new ArrayList<>();

        int count = 10;
        int start = count * page;
        int end = Math.min(result.size(), start + count);

        for (Product product: result.subList(start, end)) {
            list.add(response(product));
        }

        Pagination pagination = Pagination.builder()
                .totalPages(result.size() / count)
                .currentPage(page)
                .totalElements((long) result.size())
                .currentElements(end - start)
                .build();

        return Header.OK(list, pagination);
    }

    public Header<ProductMagazineCreateResponse> magazineProduct(Long cateIdx, Integer state, Integer order, String proName, Integer page, List<Long> idxList, List<String> nameList, List<String> imgList){
        String jpql = "select p from Product p";
        boolean check = false;

        if(cateIdx != null || state != null || proName != null){
            jpql += " where";
            if (cateIdx != null){
                jpql += " and";
                jpql += " cate_idx = :cateIdx";
                check = true;
            }
            if (state != null){
                if (check) jpql += " and";
                jpql += " pro_state = :state";
                check = true;
            }
            if (proName != null){
                if (check) jpql += " and";
                jpql += " title like :proName";
            }
        }

        switch (order){
            case 1:
                jpql += " order by idx desc";
                break;
            case 2:
                jpql += " order by idx asc";
                break;
            case 3:
                jpql += " order by price desc";
                break;
            case 4:
                jpql += " order by price asc";
                break;
        }

        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        if (cateIdx != null) query = query.setParameter("cateIdx", cateIdx);
        if (state != null) query = query.setParameter("state", state);
        if (proName != null) query = query.setParameter("proName", "%" + proName + "%");

        List<Product> result = query.getResultList();

        int count = 10;
        int start = count * page;
        int end = Math.min(result.size(), start + count);

        ProductMagazineCreateResponse productMagazineCreateResponse = ProductMagazineCreateResponse.builder()
                .idx(new ArrayList<>())
                .name(new ArrayList<>())
                .img(new ArrayList<>())
                .cateName(new ArrayList<>())
                .idxList(idxList)
                .nameList(nameList)
                .imgList(imgList)
                .build();

        for (Product product: result.subList(start, end)) {
            productMagazineCreateResponse.addIdx(product.getIdx());
            productMagazineCreateResponse.addName(product.getName());
            productMagazineCreateResponse.addImg(product.getImg1());
            productMagazineCreateResponse.addCateName(product.getCategory().getName());
        }

        Pagination pagination = Pagination.builder()
                .totalPages(result.size() / count)
                .currentPage(page)
                .totalElements((long) result.size())
                .currentElements(end - start)
                .build();

        return Header.OK(productMagazineCreateResponse, pagination);
    }

    @Transactional
    public Header<List<ProductUserListResponse>> userList(Long cateIdx, Long brIdx, Flag saleFlag, Integer page, Integer order){
        List<Category> categoryList = new ArrayList<>();
        boolean check = false;

        String jpql = "select p from Product p";
        if (cateIdx != null || brIdx != null || saleFlag != null){
            jpql += " where";
            if (cateIdx != null){
                categoryList = searchCategories(cateIdx);
                jpql += " (";
                if (categoryList.get(0).getLifeFlag() == Flag.TRUE){
                    jpql += " eve_cate_idx = :cateIdx" + 0;
                    for (int i = 1; i < categoryList.size(); i++){
                        jpql += " or eve_cate_idx = :cateIdx"+i;
                    }
                }
                else{
                    jpql += " cate_idx = :cateIdx" + 0;
                    for (int i = 1; i < categoryList.size(); i++){
                        jpql += " or cate_idx = :cateIdx"+i;
                    }
                }
                jpql += ")";
                check = true;
            }
            if (brIdx != null){
                if (check) jpql += " and";
                jpql += " br_idx = :brIdx";
                check = true;
            }
            if (saleFlag != null){
                if (check) jpql += " and";
                if (saleFlag == Flag.TRUE){
                    jpql += " sale_price != 0";
                }else{
                    jpql += " sale_price = 0";
                }
            }
        }

        switch (order){
            case 2:
                jpql += " and best_flag = 1";
            case 1:
                jpql += " order by idx desc";
                break;
            case 3:
                jpql += " order by pro_like desc";
                break;
            case 4:
                jpql += " order by price asc";
                break;
            case 5:
                jpql += " order by price desc";
                break;
        }

        TypedQuery<Product> query = em.createQuery(jpql, Product.class);

        for (int i = 0; i < categoryList.size(); i++){
            query.setParameter("cateIdx"+i, categoryList.get(i).getIdx());
        }
        if (brIdx != null) query.setParameter("brIdx", brIdx);

        List<Product> products = query.getResultList();

        int count = 40;
        int size = products.size();
        int start = count * page;
        int end = Math.min(products.size(), start + count);

        List<ProductUserListResponse> result = new ArrayList<>();

        for (Product product: products.subList(start, end)) {
            result.add(userListResponse(product, productReviewRepository.findAllByProduct(product)));
        }

        Pagination pagination = Pagination.builder()
                .totalPages(size % count == 0 ? size / count - 1 : size / count)
                .currentPage(page)
                .totalElements((long) products.size())
                .currentElements(end - start)
                .build();

        return Header.OK(result, pagination);
    }

    @Transactional
    public Header<List<ProductUserListResponse>> newProductList(String date, Integer page, Integer order){
        String jpql = "select p from Product p";
        jpql += " where";
        jpql += " TO_char(regdate, 'YYYY-MM-DD') >= :date";

        switch (order){
            case 2:
                jpql += " and best_flag = 1";
            case 1:
                jpql += " order by idx desc";
                break;
            case 3:
                jpql += " order by pro_like desc";
                break;
            case 4:
                jpql += " order by price asc";
                break;
            case 5:
                jpql += " order by price desc";
                break;
        }

        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setParameter("date", date);

        List<Product> result = query.getResultList();

        int count = 40;
        int size = result.size();
        int start = count * page;
        int end = Math.min(result.size(), start + count);

        List<ProductUserListResponse> list = new ArrayList<>();

        for (Product product: result.subList(start, end)) {
            list.add(userListResponse(product, productReviewRepository.findAllByProduct(product)));
        }

        Pagination pagination = Pagination.builder()
                .totalPages(size % count == 0 ? size / count - 1 : size / count)
                .currentPage(page)
                .totalElements((long) result.size())
                .currentElements(end - start)
                .build();

        return Header.OK(list, pagination);
    }

    @Transactional
    public Header<List<ProductUserListResponse>> saleProductList(Integer page, Integer order){
        String jpql = "select p from Product p";
        jpql += " where sale_price = null";

        switch (order){
            case 2:
                jpql += " and best_flag = 1";
            case 1:
                jpql += " order by idx desc";
                break;
            case 3:
                jpql += " order by pro_like desc";
                break;
            case 4:
                jpql += " order by price asc";
                break;
            case 5:
                jpql += " order by price desc";
                break;
        }

        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        List<Product> result = query.getResultList();

        int count = 40;
        int size = result.size();
        int start = count * page;
        int end = Math.min(result.size(), start + count);

        List<ProductUserListResponse> list = new ArrayList<>();

        for (Product product: result.subList(start, end)) {
            list.add(userListResponse(product, productReviewRepository.findAllByProduct(product)));
        }

        Pagination pagination = Pagination.builder()
                .totalPages(size % count == 0 ? size / count - 1 : size / count)
                .currentPage(page)
                .totalElements((long) result.size())
                .currentElements(end - start)
                .build();

        return Header.OK(list, pagination);
    }

    public Header<List<ProductUserListResponse>> productList(List<Long> proList){
        List<Product> products = productRepository.findAllByIdxInOrderByIdxDesc(proList);
        List<ProductUserListResponse> list = new ArrayList<>();

        for (Product product: products){
            list.add(userListResponse(product, productReviewRepository.findAllByProduct(product)));
        }

        return Header.OK(list);
    }

    @Transactional
    public Header<ProductDetailResponse> detail(Long proIdx, Long memIdx){
        Optional<Member> member = memIdx == null ? Optional.empty() : memberRepository.findById(memIdx);
        return productRepository.findById(proIdx)
                .map(product -> detailResponse(product, productReviewRepository.findAllByProductAndLikeIsNotNull(product), member.isEmpty() ? Optional.empty() : likeRepository.findByMemberAndProduct(member.get(), product)))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("상품이 없습니다"));
    }
    private ProductDetailResponse detailResponse(Product product, List<ProductReview> productReviews, Optional<Like> like){
        Float star = (float) 0;

        for (ProductReview productReview:
                productReviews) {
            star += productReview.getLike();
        }

        star = productReviews.size() == 0 ? 0 : star / productReviews.size();

        return ProductDetailResponse.builder()
                .idx(product.getIdx())
                .name(product.getName())
                .des(product.getDes())
                .brIdx(product.getBrand().getIdx())
                .brName(product.getBrand().getName())
                .brLogo(product.getBrand().getLogo())
                .netPrice(product.getNetPrice())
                .salePrice(product.getSalePrice())
                .price(product.getPrice())
                .state(product.getState())
                .star(star)
                .dateStart(product.getDateStart())
                .dateEnd(product.getDateEnd())
                .origin(product.getOrigin())
                .sizeWeight(product.getSizeWeight())
                .temp(product.getTemp())
                .count(product.getCount())
                .delivery(product.getDelivery())
                .packing(product.getPacking())
                .img1(product.getImg1())
                .img2(product.getImg2())
                .img3(product.getImg3())
                .img4(product.getImg4())
                .like(product.getLike())
                .likeFlag(like.isEmpty() ? Flag.FALSE : Flag.TRUE)
                .proDes(product.getProDes())
                .proType(product.getProType())
                .proName(product.getProName())
                .foodType(product.getFoodType())
                .producer(product.getProducer())
                .location(product.getLocation())
                .dateBuilt(product.getDateBuilt())
                .dateValid(product.getDateValid())
                .build();
    }

    private ProductUserListResponse userListResponse(Product product, List<ProductReview> productReviews){
        Float star = (float) 0;

        for (ProductReview productReview: productReviews) {
            if(productReview.getLike() != null) star += productReview.getLike();
        }

        return ProductUserListResponse.builder()
                .idx(product.getIdx())
                .name(product.getName())
                .img(product.getImg1())
                .netPrice(product.getNetPrice())
                .salePrice(product.getSalePrice())
                .price(product.getPrice())
                .count(product.getCount())
                .temp(product.getTemp())
                .bestFlag(product.getBestFlag())
                .star(star == 0? star : star/ productReviews.size())
                .regdate(product.getRegdate())
                .build();
    }

    private List<Category> searchCategories(Long cateIdx){
        Optional<Category> optional = categoryRepository.findById(cateIdx);
        List<Category> categories = new ArrayList<>();
        if (optional.isPresent()){
            Category category = optional.get();
            if (category.getRootIdx() == null) categories = categoryRepository.findAllByRootIdx(cateIdx);
            else categories.add(category);
        }
        return categories;
    }
}