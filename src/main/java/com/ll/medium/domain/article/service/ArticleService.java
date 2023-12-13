package com.ll.medium.domain.article.service;

import com.ll.medium.domain.article.entity.Article;
import com.ll.medium.domain.article.repository.ArticleRepository;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.global.Exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    public void write(String title, String body, boolean isPublished, Member member){
        Article article = new Article();
        article.setTitle(title);
        article.setBody(body);
        article.setPublished(isPublished);
        article.setCreateDate(LocalDateTime.now());
        article.setAuthor(member);
        this.articleRepository.save(article);
    }

    public void modify(Article article, String title, String body){
        article.setTitle(title);
        article.setBody(body);
        article.setModifyDate(LocalDateTime.now());
        this.articleRepository.save(article);
    }

    public void delete(Article article){
        this.articleRepository.delete(article);
    }

    public Page<Article> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("CreateDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }

    public Article getArticle(Long id) {
        Optional<Article> article = this.articleRepository.findById(id);
        if(article.isPresent()){
            return article.get();
        } else {
            throw new DataNotFoundException("article not found");
        }
    }

    public Page<Article> getHomeList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("CreateDate"));
        Pageable pageable = PageRequest.of(page, 30, Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }

    public Page<Article> getArticlesByAuthor(Member author, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("CreateDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findByAuthor(author, pageable);
    }

    public void increaseHit(Article article){
        if(article.getHit()==null){
            article.setHit(1);
            this.articleRepository.save(article);
            return;
        }
        article.setHit(article.getHit()+1);
        this.articleRepository.save(article);
    }
}
