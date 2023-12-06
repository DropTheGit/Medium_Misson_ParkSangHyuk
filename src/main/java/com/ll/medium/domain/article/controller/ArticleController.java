package com.ll.medium.domain.article.controller;

import com.ll.medium.domain.article.entity.Article;
import com.ll.medium.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private ArticleService articleService;

    @GetMapping("/post/write")
    public String write(){
        return "article_write";
    }

    @PostMapping("/post/write")
    public String write(@RequestParam String title, @RequestParam String body,
                        @RequestParam boolean isPublished){
        this.articleService.write(title, body, isPublished);
        return "redirect:/post/list";
    }

    @GetMapping("/post/list")
    public String getList(Model model){
        List<Article> articleList = new ArrayList<>();
        articleList = this.articleService.getList();
        model.addAttribute("articleList", articleList);
        return "article_list";
    }
}
