package com.ll.medium.domain.article.answer;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.service.ArticleService;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ArticleService articleService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/{id}/comment/write")
    public String addComment(Model model, @PathVariable("id") Long id, @Valid CommentForm commentForm, BindingResult bindingResult,
                             Principal principal){
        Article article = this.articleService.getArticle(id);
        Member member = this.memberService.getMember(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("article", article);
            return "domain/article/article_detail";
        }
        this.commentService.createAnswer(commentForm.getContent(), article, member);
        return String.format("redirect:/post/%d", id);
    }
}
