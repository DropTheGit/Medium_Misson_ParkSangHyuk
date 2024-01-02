package com.ll.medium.domain.article.recommend.service;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.recommend.entity.Recommend;
import com.ll.medium.domain.article.recommend.repository.RecommendRepository;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.global.Exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendRepository recommendRepository;

    public void createRecommend(Article article, Member member){
        Recommend recommend = new Recommend();
        recommend.setArticle(article);
        recommend.setMember(member);
        this.recommendRepository.save(recommend);
    }

    public Recommend getRecommend(Article article, Member member){
        Optional<Recommend> recommend = this.recommendRepository.findByArticleAndMember(article, member);
        if(recommend.isPresent()){
            return recommend.get();
        } else {
            throw new DataNotFoundException("recommend not found");
        }
    }


    public void deleteRecommend(Recommend recommend) {
        this.recommendRepository.delete(recommend);
    }
}
