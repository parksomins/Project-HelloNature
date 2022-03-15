package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.entity.Question;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.Pagination;
import com.hellonature.hellonature_back.model.network.request.QuestionApiRequest;
import com.hellonature.hellonature_back.model.network.response.QuestionApiResponse;
import com.hellonature.hellonature_back.repository.MemberRepository;
import com.hellonature.hellonature_back.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService{

    private final EntityManager em;
    private final MemberRepository memberrepository;
    private final QuestionRepository questionRepository;
    private final FileService fileService;


    public Header<QuestionApiResponse> create(QuestionApiRequest request, List<MultipartFile> multipartFiles) {
        List<String> pathList = fileService.imagesUploads(multipartFiles, "question");

        Question question = Question.builder()
                .member(memberrepository.findById(request.getMemIdx()).get())
                .ansDate(request.getAnsDate())
                .content(request.getContent())
                .ansContent(request.getAnsContent())
                .files(pathList.get(0))
                .email(request.getEmail())
                .hp(request.getHp())
                .type(request.getType())
                .build();
        questionRepository.save(question);
        return Header.OK();
    }


    @Transactional
    public Header<QuestionApiResponse> read(Long idx) {
        return questionRepository.findById(idx)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("No data"));
    }


    public Header<QuestionApiResponse> update(QuestionApiRequest questionApiRequest, List<MultipartFile> multipartFiles) {
        Optional<Question> optional = questionRepository.findById(questionApiRequest.getIdx());
        return optional.map(question -> {
                    question.setAnsFlag(questionApiRequest.getAnsFlag());
                    question.setAnsDate(questionApiRequest.getAnsDate());
                    question.setContent(questionApiRequest.getContent());
                    question.setAnsContent(questionApiRequest.getAnsContent());
                    question.setEmail(questionApiRequest.getEmail());
                    question.setHp(questionApiRequest.getHp());
                    question.setType(questionApiRequest.getType());

                    if (!multipartFiles.isEmpty()){
                        List<String> pathList = fileService.imagesUploads(multipartFiles, "question");
                        question.setFiles(pathList.get(0));
                    }

                    return question;
                }).map(questionRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("수정 실패"));
    }


    public Header delete(Long idx) {
        Optional<Question> optional = questionRepository.findById(idx);
        return optional.map(question -> {
            questionRepository.delete(question);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("No data"));
    }

    private QuestionApiResponse response(Question question){
        return QuestionApiResponse.builder()
                .idx(question.getIdx())
                .memIdx(question.getMember().getIdx())
                .name(question.getMember().getName())
                .ansFlag(question.getAnsFlag())
                .ansDate(question.getAnsDate())
                .content(question.getContent())
                .ansContent(question.getAnsContent())
                .files(question.getFiles())
                .email(question.getEmail())
                .hp(question.getHp())
                .type(question.getType())
                .regdate(question.getRegdate())
                .build();
    }


    public Header<List<QuestionApiResponse>> list(String dateStart, String dateEnd, Flag ansFlag, Integer type, String memEmail, Integer startPage){
        String jpql = "select q from Question q";
        boolean check = false;

        if(dateStart != null || dateEnd != null || ansFlag != null || type != null || memEmail != null ){
            jpql += " where";
            if (dateStart != null){
                jpql += " TO_char(regdate, 'YYYY-MM-DD') >= :dateStart";
                check = true;
            }
            if(dateEnd != null){
                if (check) jpql += " and";
                jpql += " TO_char(regdate, 'YYYY-MM-DD') <= :dateEnd";
            }
            if(ansFlag != null){
                if (check) jpql += " and";
                jpql += " ans_flag = :ansFlag";
                check = true;
            }
            if (type != null){
                if (check) jpql += " and";
                jpql += " que_type = :type";
                check = true;
            }

            if (memEmail != null){
                if (check) jpql += " and";
                jpql += " email like :memEmail";
            }
        }

        jpql += " order by q.idx desc";
        TypedQuery<Question> query = em.createQuery(jpql, Question.class);

        if (dateStart != null) query = query.setParameter("dateStart", dateStart);
        if (dateEnd != null) query = query.setParameter("dateEnd", dateEnd);
        if (ansFlag != null) query = query.setParameter("ansFlag", ansFlag.getId());
        if (type != null) query = query.setParameter("type", type);
        if (memEmail != null) query = query.setParameter("memEmail", "%" + memEmail + "%");

        List<Question> result = query.getResultList();

        int count = 10;
        int start = count * startPage;
        int end = Math.min(result.size(), start + count);

        List<QuestionApiResponse> list = new ArrayList<>();

        for(Question question : result.subList(start,end)){
            list.add(response(question));
        }

        Pagination pagination = new Pagination().builder()
                .totalPages(result.size() / count)
                .totalElements((long) result.size())
                .currentPage(startPage)
                .currentElements(end - start)
                .build();

        return Header.OK(list, pagination);
    }


    public Header<List<QuestionApiResponse>> search(Pageable pageable){
        Page<Question> question = questionRepository.findAll(pageable);
        List<QuestionApiResponse> questionApiResponseList = question.stream()
                .map(this::response)
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(question.getTotalPages())
                .totalElements(question.getTotalElements())
                .currentPage(question.getNumber())
                .currentElements(question.getNumber())
                .build();
        return Header.OK(questionApiResponseList, pagination);
    }

    @Transactional
    public Header deletePost(List<Long> idx) {
        questionRepository.deleteAllByIdxIn(idx);
        return Header.OK();
    }

    public Header<List<QuestionApiResponse>> myPageList(Long memIdx){
        Optional<Member> optionalMember = memberrepository.findById(memIdx);
        if (optionalMember.isEmpty()) return Header.ERROR("회원 정보가 없습니다");

        List<Question> questions = questionRepository.findAllByMember(optionalMember.get());
        List<QuestionApiResponse> questionApiResponses = new ArrayList<>();
        for (Question question: questions){
            questionApiResponses.add(response(question));
        }
        return Header.OK(questionApiResponses);
    }

    public Header<QuestionApiResponse> updateAns(Header<QuestionApiRequest> requestHeader){
        QuestionApiRequest request = requestHeader.getData();
        Optional<Question> optional = questionRepository.findById(request.getIdx());
        if (optional.isEmpty()) Header.ERROR("1:1문의 idx가 잘못되었습니다");
        Question question = optional.get();

        if (request.getAnsContent() == null || request.getAnsContent().isEmpty()){
            question.setAnsContent(null);
            question.setAnsFlag(Flag.FALSE);
            question.setAnsDate(null);
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            question.setAnsContent(request.getAnsContent());
            question.setAnsFlag(Flag.TRUE);
            question.setAnsDate(LocalDate.now().format(formatter));
        }

        questionRepository.save(question);

        return Header.OK();
    }
}
