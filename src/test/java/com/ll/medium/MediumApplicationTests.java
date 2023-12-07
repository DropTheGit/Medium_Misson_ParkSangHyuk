package com.ll.medium;

import com.ll.medium.domain.article.entity.Article;
import com.ll.medium.domain.article.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class MediumApplicationTests {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void testJpa(){
        for(int i = 0; i < 100 ; i++){
            Article article = new Article();
            article.setTitle(String.format("테스트제목%d", i));
            article.setBody(String.format("테스트내용%d", i));
            article.setCreateDate(LocalDateTime.now());
            article.setPublished(true);
            this.articleRepository.save(article);
        }

    }

}
