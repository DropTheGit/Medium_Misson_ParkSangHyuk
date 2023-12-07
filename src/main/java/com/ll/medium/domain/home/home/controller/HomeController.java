package com.ll.medium.domain.home.home.controller;

import com.ll.medium.domain.article.entity.Article;
import com.ll.medium.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ArticleService articleService;

    @GetMapping("/")
    public String showHome(Model model){
        Page<Article> paging = this.articleService.getHomeList(1);
        model.addAttribute("paging", paging);
        return "home_list";
    }

}
