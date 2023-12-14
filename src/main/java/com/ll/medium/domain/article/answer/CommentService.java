package com.ll.medium.domain.article.answer;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void createAnswer(String content, Article article, Member member){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setArticle(article);
        comment.setAuthor(member);
        comment.setCreateDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }
}
