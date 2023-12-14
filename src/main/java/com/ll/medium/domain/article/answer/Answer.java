package com.ll.medium.domain.article.answer;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Member author;
}
