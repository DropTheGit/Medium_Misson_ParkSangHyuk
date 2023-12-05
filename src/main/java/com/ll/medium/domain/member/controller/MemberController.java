package com.ll.medium.domain.member.controller;

import com.ll.medium.domain.member.MemberCreateForm;
import com.ll.medium.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/member/join")
    public String join(MemberCreateForm memberCreateForm){
        return "join_form";
    }

    @PostMapping("/member/join")
    public String join(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "join_form";
        }

        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())){
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "join_form";
        }

        this.memberService.join(memberCreateForm.getUsername(), memberCreateForm.getPassword1());

        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String login() {
        return "login_form";
    }
}
