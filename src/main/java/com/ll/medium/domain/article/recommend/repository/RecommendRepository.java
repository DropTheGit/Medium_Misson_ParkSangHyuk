package com.ll.medium.domain.article.recommend.repository;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.recommend.entity.Recommend;
import com.ll.medium.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    Optional<Recommend> findByArticleAndMember(Article article, Member member);
}
