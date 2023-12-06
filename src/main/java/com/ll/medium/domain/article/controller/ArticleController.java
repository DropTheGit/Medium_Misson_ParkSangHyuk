package com.ll.medium.domain.article.controller;

import com.ll.medium.domain.article.entity.Article;
import com.ll.medium.domain.article.entity.ArticleForm;
import com.ll.medium.domain.article.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/post/write")
    public String write(ArticleForm articleForm){
        return "article_write";
    }

    @PostMapping("/post/write")
    public String write(@Valid ArticleForm articleForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "article_write";
        }
        this.articleService.write(articleForm.getTitle(), articleForm.getBody(),
                articleForm.isPublished());
        return "redirect:/post/list";
    }

    @GetMapping("/post/list")
    public String getList(Model model){
        List<Article> articleList = new ArrayList<>();
        articleList = this.articleService.getList();
        model.addAttribute("articleList", articleList);
        return "article_list";
    }

    @GetMapping("post/{id}")
    public String showDetail(Model model, @PathVariable("id") Long id){
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_detail";
    }
}
