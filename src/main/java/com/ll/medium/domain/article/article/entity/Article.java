package com.ll.medium.domain.article.article.entity;

import com.ll.medium.domain.article.answer.Answer;
import com.ll.medium.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private LocalDateTime createDate;

    private boolean isPublished;

    @ManyToOne
    private Member author;

    private LocalDateTime modifyDate;

    private Integer hit;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}
