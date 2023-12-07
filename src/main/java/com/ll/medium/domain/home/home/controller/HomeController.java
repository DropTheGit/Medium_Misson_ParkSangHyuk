package com.ll.medium.domain.home.home.controller;

import com.ll.medium.domain.article.entity.Article;
import com.ll.medium.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ArticleService articleService;

    @GetMapping("/")
    public String showHome(Model model){
        List<Article> articles = this.articleService.getList();
        model.addAttribute("articleList", articles);
        return "home_list";
    }

}
