package com.ll.medium.domain.article.answer;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.global.Exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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


    public Comment getComment(Long id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if(comment.isPresent()){
            return comment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public void modifyComment(Comment comment, String content){
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    public void deleteComment(Comment comment){
        this.commentRepository.delete(comment);
    }
}
