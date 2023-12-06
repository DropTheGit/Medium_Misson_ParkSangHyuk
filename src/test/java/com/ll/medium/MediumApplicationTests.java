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
        Article a1 = new Article();
        a1.setTitle("제목1");
        a1.setBody("내용1");
        a1.setPublished(true);
        a1.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(a1);

        Article a2 = new Article();
        a2.setTitle("제목2");
        a2.setBody("내용2");
        a2.setPublished(true);
        a2.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(a2);
    }

}
