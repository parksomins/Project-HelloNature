package com.hellonature.hellonature_back.service;


import com.hellonature.hellonature_back.model.entity.Notice;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.NoticeApiRequest;
import com.hellonature.hellonature_back.model.network.response.NoticeApiResponse;
import com.hellonature.hellonature_back.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService extends BaseService<NoticeApiRequest, NoticeApiResponse, Notice>{

    private final EntityManager em;
    private final NoticeRepository noticeRepository;

    @Override
    public Header<NoticeApiResponse> create(Header<NoticeApiRequest> request) {
        NoticeApiRequest noticeApiRequest = request.getData();

        Notice notice = Notice.builder()

                .type(noticeApiRequest.getType())
                .title(noticeApiRequest.getTitle())
                .content(noticeApiRequest.getContent())
                .build();
        Notice newNotice = noticeRepository.save(notice);

        return Header.OK();
    }

    @Override
    public Header<NoticeApiResponse> read(Long idx) {
        return noticeRepository.findById(idx)
                .map(notice -> response(notice))
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }

    @Override
    public Header<NoticeApiResponse> update(Header<NoticeApiRequest> request) {
        NoticeApiRequest noticeApiRequest = request.getData();
        Optional<Notice> optional = noticeRepository.findById(noticeApiRequest.getIdx());
        return optional.map(notice -> {
                    notice.setType(noticeApiRequest.getType());
                    notice.setTitle(noticeApiRequest.getTitle());
                    notice.setContent(noticeApiRequest.getContent());

                    return notice;
                }).map(notice -> noticeRepository.save(notice))
                .map(notice -> response(notice))
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("No data"));
    }

    @Override
    public Header delete(Long idx) {
        Optional<Notice> optional = noticeRepository.findById(idx);
        return optional.map(notice -> {
            noticeRepository.delete(notice);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("No data"));
    }

    private NoticeApiResponse response(Notice notice){
        NoticeApiResponse noticeApiResponse = NoticeApiResponse.builder()
                .idx(notice.getIdx())
                .type(notice.getType())
                .title(notice.getTitle())
                .content(notice.getContent())
                .regdate(notice.getRegdate())
                .build();
        return noticeApiResponse;
    }

    // 번호 제목 내용 전시상태 등록일
    public Header<List<Notice>> list(Integer type, String title,  String dateStart, String dateEnd, Integer startPage){
        String jpql = "select n from Notice n";
        boolean check = false;

        if(type != null || title != null  ||  dateStart != null || dateEnd != null){
            jpql += " where";
            if(type != null){
                jpql += " n_type = :type";
                check = true;
            }
            if (title != null){
                if(check) jpql += " and";
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
        }

        jpql += " order by idx desc";
        TypedQuery<Notice> query = em.createQuery(jpql, Notice.class);

        if (type != null) query = query.setParameter("type", type);
        if (title != null) query = query.setParameter("title", "%"+title+"%");
        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);

        List<Notice> result = query.getResultList();

        int count = 10;

        int start = (count * startPage);
        int end = Math.min(result.size(), start + count);

        Pagination pagination = new Pagination().builder()
                .totalPages(result.size()/count)
                .totalElements(noticeRepository.count())
                .currentPage(startPage)
                .currentElements(result.size())
                .build();

        return Header.OK(result.subList(start, end), pagination);
    }

    public Header<List<NoticeApiResponse>> search(Pageable pageable){
        Page<Notice> notice = noticeRepository.findAll(pageable);
        List<NoticeApiResponse> noticeApiResponseList = notice.stream()
                .map(notices -> response(notices))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(notice.getTotalPages()-1)
                .totalElements(notice.getTotalElements())
                .currentPage(notice.getNumber())
                .currentElements(notice.getNumberOfElements())
                .build();
        return Header.OK(noticeApiResponseList, pagination);
    }

    @Transactional
    public Header deletePost(List<Long> idx) {
        noticeRepository.deleteAllByIdxIn(idx);
        return Header.OK();
    }
}