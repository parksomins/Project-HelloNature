package com.hellonature.hellonature_back.controller.api;

import com.hellonature.hellonature_back.controller.CrudController;
import com.hellonature.hellonature_back.model.SecurityMemberLoginDTO;
import com.hellonature.hellonature_back.model.entity.Member;
import com.hellonature.hellonature_back.model.enumclass.Flag;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.model.network.request.MemberApiRequest;
import com.hellonature.hellonature_back.model.network.request.MemberCreateRequest;
import com.hellonature.hellonature_back.model.network.response.MemberApiResponse;
import com.hellonature.hellonature_back.model.network.response.MemberMyPageResponse;
import com.hellonature.hellonature_back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController extends CrudController<MemberApiRequest, MemberApiResponse, Member> {

    private final MemberService memberService;

    @Override
    @PostMapping("/create")
    public Header<MemberApiResponse> create(@RequestBody Header<MemberApiRequest> request) {
        return memberService.create(request);
    }

    @Override
    @GetMapping("/read/{idx}")
    public Header<MemberApiResponse> read(@PathVariable(name = "idx") Long idx) {
        return memberService.read(idx);
    }

    @Override
    @PutMapping("/update")
    public Header<MemberApiResponse> update(@RequestBody Header<MemberApiRequest> request) {
        return memberService.update(request);
    }

    @Override
    @DeleteMapping("/delete/{idx}")
    public Header delete(@PathVariable(name = "idx") Long idx) {
        return memberService.delete(idx);
    }

    @GetMapping("/list")
    public Header<List<MemberApiResponse>> list(@RequestParam(name = "dateStart", required = false) String dateStart,
                                                @RequestParam(name = "dateEnd", required = false) String dateEnd,
                                                @RequestParam(name = "email", required = false) String email,
                                                @RequestParam(name = "name", required = false) String name,
                                                @RequestParam(name = "hp", required = false) String hp,
                                                @RequestParam(name = "startPage", defaultValue = "0") Integer startPage){
        return memberService.list(dateStart, dateEnd, email, name, hp, startPage);
    }

    @GetMapping("myPage/{idx}")
    public Header<MemberMyPageResponse> myPage(@PathVariable(name = "idx") Long idx){
        return memberService.myPage(idx);
    }

    @PostMapping("signUp")
    public Header<MemberApiResponse> signUp(@RequestBody Header<MemberCreateRequest> memberCreateRequest){
        return memberService.signUp(memberCreateRequest);
    }

    @GetMapping("emailCheck/{email}")
    public Header<Flag> emailCheck(@PathVariable(name = "email") String email){
        return memberService.emailCheck(email);
    }

    @GetMapping("editPassword")
    public Header passwordCheck(@RequestParam(name = "email") String email,
                                @RequestParam(name = "password") String password){
        return memberService.editPassword(email, password);
    }

    @DeleteMapping("/deleteList/{idx}")
    public Header delete(@PathVariable("idx") List<Long> idx) {
        return memberService.deletePost(idx);
    }
}
