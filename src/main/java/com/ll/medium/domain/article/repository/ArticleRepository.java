package com.ll.medium.domain.article.repository;

import com.ll.medium.domain.article.entity.Article;
import com.ll.medium.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

   Page<Article> findAll(Pageable pageable);

   Page<Article> findByAuthor(Member author, Pageable pageable);
}
