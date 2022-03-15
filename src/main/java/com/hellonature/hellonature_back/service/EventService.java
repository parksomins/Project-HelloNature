package com.hellonature.hellonature_back.service;


import com.hellonature.hellonature_back.model.entity.Event;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.EventApiRequest;
import com.hellonature.hellonature_back.model.network.response.EventApiResponse;
import com.hellonature.hellonature_back.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService{

    private final EntityManager em;
    private final EventRepository eventRepository;
    private final FileService fileService;



    public Header<EventApiResponse> create(EventApiRequest eventApiRequest, List<MultipartFile> multipartFiles) {
        List<String> pathList = fileService.imagesUploads(multipartFiles, "event");

        Event event = Event.builder()
                .typeFlag(eventApiRequest.getTypeFlag())
                .ingFlag(eventApiRequest.getIngFlag())
                .dateStart(eventApiRequest.getDateStart())
                .dateEnd(eventApiRequest.getDateEnd())
                .img(pathList.isEmpty() ? null : pathList.get(0))
                .title(eventApiRequest.getTitle())
                .des(eventApiRequest.getDes())
                .content(eventApiRequest.getContent())
                .build();
        Event newEvent = eventRepository.save(event);
        return Header.OK();
    }

    public Header<EventApiResponse> read(Long idx) {
        return eventRepository.findById(idx)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("No data"));
    }

    public Header<EventApiResponse> update(EventApiRequest eventApiRequest, List<MultipartFile> multipartFiles) {
        Optional<Event> optional = eventRepository.findById(eventApiRequest.getIdx());
        return optional.map(event -> {
            event.setTypeFlag(eventApiRequest.getTypeFlag());
            event.setIngFlag(eventApiRequest.getIngFlag());
            event.setDateStart(eventApiRequest.getDateStart());
            event.setDateEnd(eventApiRequest.getDateEnd());
            if (multipartFiles != null && !multipartFiles.isEmpty()){
                List<String> pathList = fileService.imagesUploads(multipartFiles, "event");
                if (pathList.get(0) != null) event.setImg(pathList.get(0));
            }
            event.setTitle(eventApiRequest.getTitle());
            event.setDes(eventApiRequest.getDes());
            event.setContent(eventApiRequest.getContent());

            return event;
        }).map(eventRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }

    public Header delete(Long idx) {
        Optional<Event> optional = eventRepository.findById(idx);

        return optional.map(event -> {
            eventRepository.delete(event);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("No data"));
    }

    private EventApiResponse response(Event event){
        return EventApiResponse.builder()
                .idx(event.getIdx())
                .typeFlag(event.getTypeFlag())
                .ingFlag(event.getIngFlag())
                .dateStart(event.getDateStart())
                .dateEnd(event.getDateEnd())
                .img(event.getImg())
                .title(event.getTitle())
                .des(event.getDes())
                .content(event.getContent())
                .build();
    }

    public Header<List<EventApiResponse>> list(Flag typeFlag, String title, String dateStart, String dateEnd, Flag ingFlag, Integer page){
        String jpql = "select e from Event e";
        boolean check = false;

        if(typeFlag != null ||title != null || dateStart != null || dateEnd != null || ingFlag != null){
            jpql += " where";
            if(typeFlag != null){
                jpql += " type_flag = :typeFlag";
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
                check = true;
            }
            if (ingFlag != null){
                if (check) jpql += " and";
                jpql += " ing_flag = :ingFlag";
            }
        }

        jpql += " order by idx desc";
        TypedQuery<Event> query = em.createQuery(jpql, Event.class);

        if (typeFlag != null) query = query.setParameter("typeFlag", typeFlag);
        if (title != null) query = query.setParameter("title", "%"+title+"%");
        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);
        if (ingFlag != null) query = query.setParameter("ingFlag", ingFlag.getId());

        List<Event> result = query.getResultList();

        int count = 10;
        int start = count * page;
        int end = Math.min(result.size(), start + count);

        List<EventApiResponse> list = new ArrayList<>();

        for (Event event: result.subList(start, end)){
            list.add(response(event));
        }

        Pagination pagination = Pagination.builder()
                .totalPages(result.size() / count)
                .currentPage(page)
                .totalElements((long) result.size())
                .currentElements(end - start)
                .build();


        return Header.OK(list, pagination);
    }
    public Header<List<EventApiResponse>> search(Pageable pageable){
        Page<Event> event = eventRepository.findAll(pageable);
        List<EventApiResponse> eventApiResponseList = event.stream()
                .map(this::response)
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(event.getTotalPages()-1)
                .totalElements(event.getTotalElements())
                .currentPage(event.getNumber())
                .currentElements(event.getNumberOfElements())
                .build();
        return Header.OK(eventApiResponseList, pagination);
    }

    @Transactional
    public Header deletePost(List<Long> idx) {
        eventRepository.deleteAllByIdxIn(idx);
        return Header.OK();
    }
}

