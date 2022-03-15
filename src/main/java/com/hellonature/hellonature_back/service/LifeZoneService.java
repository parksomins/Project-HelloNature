package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Product;
import com.hellonature.hellonature_back.model.entity.ProductReview;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.ProductApiRequest;
import com.hellonature.hellonature_back.model.network.response.ProductApiResponse;
import com.hellonature.hellonature_back.model.network.response.ProductReviewApiResponse;
import com.hellonature.hellonature_back.repository.ProductRepository;
import com.hellonature.hellonature_back.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LifeZoneService{

    private ProductRepository productRepository;

    public Header<List<ProductApiResponse>> search(Pageable pageable){
        Page<Product> product = productRepository.findAll(pageable);
        List<ProductApiResponse> productApiResponseList = product.stream()
                .map(products -> response(products))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(product.getTotalPages()-1)
                .totalElements(product.getTotalElements())
                .currentPage(product.getNumber())
                .currentElements(product.getNumberOfElements())
                .build();
        return Header.OK(productApiResponseList, pagination);
    }

    public Header<List<ProductApiResponse>> productList(Long eveCateIdx){
        List<Product> products = productRepository.findAllByEveCategoryIdxOrderByIdxDesc(eveCateIdx);

        List<ProductApiResponse> list = new ArrayList<>();

        for (Product product: products) {
            list.add(response(product));
        }

        return Header.OK(list);
    }

    @Transactional
    public Header deleteList(List<Long> proList){
        List<Product> products = productRepository.findAllByIdxIn(proList);
        for (Product product: products) {
            product.setEveCategory(null);
            productRepository.save(product);
        }
        return Header.OK();
    }

    private ProductApiResponse response(Product product){
        ProductApiResponse productApiResponse = ProductApiResponse.builder()
                .idx(product.getIdx())
                .name(product.getName())
                .netPrice(product.getNetPrice())
                .salePrice(product.getSalePrice())
                .state(product.getState())
                .img1(product.getImg1())
                .like(product.getLike())
                .eveCateIdx(product.getEveCategory().getIdx())
                .build();
        return productApiResponse;
    }
}
