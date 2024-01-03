package com.ll.medium.domain.article.article.entity;

import com.ll.medium.domain.article.answer.entity.Comment;
import com.ll.medium.domain.article.recommend.entity.Recommend;
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

    private boolean isPaid;

    @ManyToOne
    private Member author;

    private LocalDateTime modifyDate;

    private Integer hit;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Recommend> recommendList;
}
