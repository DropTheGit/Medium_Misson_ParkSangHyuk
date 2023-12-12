package com.ll.medium.domain.article.controller;

import com.ll.medium.domain.article.entity.Article;
import com.ll.medium.domain.article.entity.ArticleForm;
import com.ll.medium.domain.article.service.ArticleService;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/write")
    public String write(ArticleForm articleForm){
        return "article_write";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/write")
    public String write(@Valid ArticleForm articleForm, BindingResult bindingResult,
                        Principal principal){
        if(bindingResult.hasErrors()){
            return "article_write";
        }
        Member member = memberService.getMember(principal.getName());
        this.articleService.write(articleForm.getTitle(), articleForm.getBody(),
                articleForm.isPublished(), member);
        return "redirect:/post/list";
    }

    @GetMapping("/post/list")
    public String getList(Model model,
                          @RequestParam(value="page", defaultValue="0") int page){
        Page<Article> paging = this.articleService.getList(page);
        model.addAttribute("paging", paging);
        return "article_list";
    }

    @GetMapping("post/{id}")
    public String showDetail(Model model, @PathVariable("id") Long id){
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_detail";
    }

    @GetMapping("/post/{id}/modify")
    @PreAuthorize("isAuthenticated()")
    public String articleModify(ArticleForm articleForm,
                         @PathVariable("id") Long id, Principal principal){
        Article article = this.articleService.getArticle(id);
        if(!article.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        articleForm.setTitle(article.getTitle());
        articleForm.setBody(article.getBody());
        return "article_modify";
    }

    @PutMapping("/post/{id}/modify")
    @PreAuthorize("isAuthenticated()")
    public String articleModify(@PathVariable("id") Long id, Principal principal,
                                @Valid ArticleForm articleForm, BindingResult bindiResult){
        if(bindiResult.hasErrors()){
            return "article_modify";
        }
        Article article = this.articleService.getArticle(id);
        if(!article.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.articleService.modify(article, articleForm.getTitle(), articleForm.getBody());
        return String.format("redirect:/post/%d", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/{id}/delete")
    public String articleDelete(Principal principal, @PathVariable("id") Long id){
        Article article = this.articleService.getArticle(id);
        if(!article.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.articleService.delete(article);
        return "redirect:/post/list";
    }

    @GetMapping("/post/b/{username}")
    public String userArticle(@PathVariable("username") String username, Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page) {

        Member member = this.memberService.getMember(username);
        Page<Article> paging = this.articleService.getArticlesByAuthor(member, page);
        model.addAttribute("paging", paging);
        return "article_user";
    }
}
