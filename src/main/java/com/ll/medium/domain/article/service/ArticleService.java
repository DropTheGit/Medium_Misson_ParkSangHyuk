package com.ll.medium.domain.article.service;

import com.ll.medium.domain.article.entity.Article;
import com.ll.medium.domain.article.repository.ArticleRepository;
import com.ll.medium.global.Exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    public void write(String title, String body, boolean isPublished){
        Article article = new Article();
        article.setTitle(title);
        article.setBody(body);
        article.setPublished(isPublished);
        this.articleRepository.save(article);
    }

    public List<Article> getList(){
        return this.articleRepository.findAll();
    }

    public Article getArticle(Long id) {
        Optional<Article> article = this.articleRepository.findById(id);
        if(article.isPresent()){
            return article.get();
        } else {
            throw new DataNotFoundException("article not found");
        }
    }
}
