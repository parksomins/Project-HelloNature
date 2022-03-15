package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Category;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.CategoryApiRequest;
import com.hellonature.hellonature_back.model.network.response.CategoryApiResponse;
import com.hellonature.hellonature_back.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService  {

    @Autowired(required = false)
    CategoryRepository categoryRepository;

    private final FileService fileService;


    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {
        CategoryApiRequest categoryApiRequest = request.getData();

        Category category = Category.builder()
                .img(categoryApiRequest.getImg())
                .name(categoryApiRequest.getName())
                .lifeFlag(categoryApiRequest.getLifeFlag())
                .rootIdx(categoryApiRequest.getRootIdx())
                .build();
        categoryRepository.save(category);
        return Header.OK();
    }


    public Header<CategoryApiResponse> read(Long idx) {
        return categoryRepository.findById(idx)
                .map(category -> response(category))
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("No data")
                );

    }

    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request, List<MultipartFile> multipartFiles) {
        List<String> pathList = fileService.imagesUploads(multipartFiles, "category");
        CategoryApiRequest categoryApiRequest = request.getData();
        Optional<Category> optional = categoryRepository.findById(categoryApiRequest.getIdx());
        return optional.map(category -> {
                    category.setImg(pathList.get(0));
                    category.setLifeFlag(categoryApiRequest.getLifeFlag());
                    category.setName(categoryApiRequest.getName());
                    category.setRootIdx(categoryApiRequest.getRootIdx());

                    return category;
                }).map(category -> categoryRepository.save(category))
                .map(category -> response(category))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("수정 실패"));
    }


    public Header delete(Long idx) {
        Optional<Category> optional = categoryRepository.findById(idx);

        return optional.map(category -> {
            categoryRepository.delete(category);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private CategoryApiResponse response(Category category){
        CategoryApiResponse categoryApiResponse = CategoryApiResponse.builder()
                .idx(category.getIdx())
                .img(category.getImg())
                .lifeFlag(category.getLifeFlag())
                .name(category.getName())
                .rootIdx(category.getRootIdx())
                .build();
        return categoryApiResponse;
    }

    public Header<List<CategoryApiResponse>> first(Flag lifeFlag){
        Optional<List<Category>> optional = categoryRepository.findAllByRootIdxIsNullAndLifeFlagOrderByIdx(lifeFlag);
        List<Category> list = optional.get();
        List<CategoryApiResponse> newList = new ArrayList<>();
        for (Category category:
                list) {
            newList.add(response(category));
        }
        return Header.OK(newList);
    }

    public Header<List<CategoryApiResponse>> second(Long idx, Flag lifeFlag){
        Optional<List<Category>> optional = categoryRepository.findAllByRootIdxAndLifeFlag(idx, lifeFlag);
        List<Category> list = optional.get();
        List<CategoryApiResponse> newList = new ArrayList<>();
        for (Category category:
                list) {
            newList.add(response(category));
        }
        return Header.OK(newList);
    }
}