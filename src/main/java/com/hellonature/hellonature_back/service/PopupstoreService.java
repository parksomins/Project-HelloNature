package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Popupstore;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.PopupstoreApiRequest;
import com.hellonature.hellonature_back.model.network.response.PopupstoreApiResponse;
import com.hellonature.hellonature_back.repository.BrandRepository;
import com.hellonature.hellonature_back.repository.PopupstoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PopupstoreService {

    private final EntityManager em;
    private final PopupstoreRepository popupstoreRepository;
    private final BrandRepository brandRepository;
    private final FileService fileService;


    public Header<PopupstoreApiResponse> create(PopupstoreApiRequest request,  List<MultipartFile> multipartFiles) {
        List<String> pathList = fileService.imagesUploads(multipartFiles, "popupstore");

        Popupstore popupstore = Popupstore.builder()
                .img(pathList.get(0))
                .title(request.getTitle())
                .des(request.getDes())
                .brand(brandRepository.findById(request.getBrIdx()).get())
                .content(request.getContent())
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .build();
        popupstoreRepository.save(popupstore);
        return Header.OK();
    }


    public Header<PopupstoreApiResponse> read(Long id) {
        return popupstoreRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }


    public Header<PopupstoreApiResponse> update(PopupstoreApiRequest popupstoreApiRequest, List<MultipartFile> multipartFiles) {
        Optional<Popupstore> optional = popupstoreRepository.findById(popupstoreApiRequest.getIdx());
        return optional.map(popupstore -> {
            if (!multipartFiles.isEmpty()){
                List<String> pathList = fileService.imagesUploads(multipartFiles, "popupstore");
                if (pathList.get(0) != null) popupstore.setImg(pathList.get(0));
            }

            popupstore.setTitle(popupstoreApiRequest.getTitle());
            popupstore.setDes(popupstoreApiRequest.getDes());
            popupstore.setBrand(brandRepository.findById(popupstoreApiRequest.getBrIdx()).get());
            popupstore.setContent(popupstoreApiRequest.getContent());
            popupstore.setDateStart(popupstoreApiRequest.getDateStart());
            popupstore.setDateEnd(popupstoreApiRequest.getDateEnd());

            return popupstore;
        }).map(popupstoreRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("수정 실패"));
    }


    public Header delete(Long id) {
        Optional<Popupstore> optional = popupstoreRepository.findById(id);

        return optional.map(popupstore -> {
            popupstoreRepository.delete(popupstore);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("No data"));
    }

    private PopupstoreApiResponse response(Popupstore popupstore){
        return PopupstoreApiResponse.builder()
                .idx(popupstore.getIdx())
                .img(popupstore.getImg())
                .title(popupstore.getTitle())
                .des(popupstore.getDes())
                .brIdx(popupstore.getBrand().getIdx())
                .content(popupstore.getContent())
                .dateStart(popupstore.getDateStart())
                .dateEnd(popupstore.getDateEnd())
                .build();
    }

    // 번호 제목 내용 전시상태 등록일
    public Header<List<Popupstore>> list(Long idx, String img, String title, String des, Long brIdx, String content, String dateStart, String dateEnd, Integer startPage){
        String jpql = "select p from Popupstore p";
        boolean check = false;

        if(idx != null || img != null || title != null || des!= null || brIdx!=null || content != null || dateStart != null || dateEnd != null){
            jpql += " where";
            if(idx != null){
                jpql += " idx = :idx";
                check = true;
            }
            if (img != null){
                if (check) jpql += " and";
                jpql += " img like :img";
                check = true;
            }
            if (title != null){
                if (check) jpql += " and";
                jpql += " title like :title";
                check = true;
            }
            if (des != null){
                if (check) jpql += " and";
                jpql += " des like :des";
                check = true;
            }
            if (brIdx != null){
                if (check) jpql += " and";
                jpql += " br_idx like :brIdx";
                check = true;
            }
            if (content != null){
                if (check) jpql += " and";
                jpql += " pop_content like :content";
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
        TypedQuery<Popupstore> query = em.createQuery(jpql, Popupstore.class);

        if (idx != null) query = query.setParameter("idx", idx);
        if (img != null) query = query.setParameter("img", img);
        if (title != null) query = query.setParameter("title", "%"+title+"%");
        if (des != null) query = query.setParameter("des", "%"+des+"%");
        if (brIdx != null) query = query.setParameter("br_idx", brIdx);
        if (content != null) query = query.setParameter("content", "%"+content+"%");
        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);

        List<Popupstore> result = query.getResultList();

        int count = 10;

        int start = count * startPage;
        int end = Math.min(result.size(), start + count);

        Pagination pagination = new Pagination().builder()
                .totalPages(result.size() / count)
                .totalElements((long) result.size())
                .currentPage(startPage)
                .currentElements(end - start)
                .build();

        return Header.OK(result.subList(start, end), pagination);
    }
}
