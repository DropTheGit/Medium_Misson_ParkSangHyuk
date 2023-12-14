package com.ll.medium.domain.article.recommend.controller;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.service.ArticleService;
import com.ll.medium.domain.article.recommend.service.RecommendService;
import com.ll.medium.domain.article.recommend.entity.Recommend;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class RecommendController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final RecommendService recommendService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("post/{id}/like")
    public String Recommend(@PathVariable("id") Long id, Principal principal){
        Article article = this.articleService.getArticle(id);
        Member member = this.memberService.getMember(principal.getName());
        this.recommendService.createRecommend(article, member);
        return String.format("redirect:/post/%d", id);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("post/{id}/cancellike")
    public String cancelRecommend(@PathVariable("id") Long id, Principal principal){
        Article article = this.articleService.getArticle(id);
        Member member = this.memberService.getMember(principal.getName());
        Recommend recommend = this.recommendService.getRecommend(article, member);
        this.recommendService.deleteRecommend(recommend);
        return String.format("redirect:/post/%d", id);
    }

}
